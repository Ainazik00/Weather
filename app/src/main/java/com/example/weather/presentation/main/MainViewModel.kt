package com.example.weather.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.weather.model.WeatherModel
import com.example.weather.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    fun getWeather(city:String):LiveData<WeatherModel>{
        return repository.getWeather(city)
    }

    fun date(): String {
        val sdf = SimpleDateFormat(" MMMM dd yyyy", Locale.getDefault())
        return sdf.format((Date()))
    }

    fun dayName(timestamp: Long): String {
        val sdf = SimpleDateFormat("EEE", Locale.getDefault())
        return sdf.format((Date(timestamp)))
    }

}


