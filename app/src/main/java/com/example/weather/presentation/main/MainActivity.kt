package com.example.weather.presentation.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherr.R
import com.example.weatherr.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), TextWatcher {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentDate = viewModel.date()
        val currentDayOfWeek1 = viewModel.dayName(System.currentTimeMillis())
        binding.search.afterTextChanged { city ->
            viewModel.getWeather(city).observe(this) { it ->
                it.let { mainWeather ->
                    binding.search.removeTextChangedListener(this)
                    binding.search.setText(mainWeather.city.name)
                    binding.search.addTextChangedListener(this)
                    mainWeather.list.forEach {
                        binding.tvTemp.text = "${it.main.temp}"
                        binding.tvHumidity.text = "${it.main.humidity} %"
                        binding.tvMaxTemp.text = "${it.main.temp_max}°C"
                        binding.tvSea.text = "${it.main.pressure} hPa"
                        binding.tvMinTemp.text = "${it.main.temp_min}°C"
                        binding.tvWindSpeed.text = "${it.wind.speed} m/s"
                        binding.tvSunRise.text = "${it.sys.sunrise}"
                        binding.tvSunSet.text = "${it.sys.sunset}"
                        binding.tvDate.text = currentDate
                        binding.tvDay2.text = currentDayOfWeek1
                        val weather = it.weather.firstOrNull()
                        if (weather != null) {
                            binding.tvWeather.text = weather.main
                            changeImages(weather.description)
                        }
                        val currentTime = System.currentTimeMillis() / 1000
                        val currentDayOfWeek =
                            Calendar.getInstance().apply { timeInMillis = currentTime * 1000 }
                                .get(Calendar.DAY_OF_WEEK)

                        binding.tvDay.text = when (currentDayOfWeek) {
                            Calendar.MONDAY -> "MONDAY"
                            Calendar.TUESDAY -> "TUESDAY"
                            Calendar.WEDNESDAY -> "WEDNESDAY"
                            Calendar.THURSDAY -> "THURSDAY"
                            Calendar.FRIDAY -> "FRIDAY"
                            Calendar.SATURDAY -> "SATURDAY"
                            Calendar.SUNDAY -> "SUNDAY"
                            else -> "Unknown"
                        }
                    }
                }
            }
        }
    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }

    private fun changeImages(conditions: String): Pair<Int, Int> {
        return when (conditions) {
            "Clear Sky", "Sunny", "Clear" -> Pair(R.drawable.img_4, R.drawable.back_sun)
            "Partly Cloudy", "Clouds", "Overcast", "Mist", "Forge" -> Pair(
                R.drawable.img_1,
                R.drawable.colin
            )

            "Light Rain", "Drizzle", "Moderate Rain", "Showers", "Heavy Rain" -> Pair(
                R.drawable.cloud,
                R.drawable.severin
            )

            "Light Snow", "Moderate Snow", "heavy Snow", "Blizzard" -> Pair(
                R.drawable.img_2,
                R.drawable.back_snow
            )

            else -> Pair(R.drawable.img_4, R.drawable.back_sun)
        }
    }

}


