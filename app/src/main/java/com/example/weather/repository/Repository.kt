package com.example.weather.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.weather.model.City
import com.example.weather.model.WeatherApi
import com.example.weather.model.WeatherModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val api: WeatherApi) {
    fun getWeather(city: String): MutableLiveData<WeatherModel> {
        val data = MutableLiveData<WeatherModel>()

        api.getWeather(city = city).enqueue(object : Callback<WeatherModel> {
            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        data.value = it
                    }
                }
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                Log.e("ololo", "onFailure: ${t.message}")
            }
        })
        return data
    }

}