package com.example.weather

import com.example.weather.model.WeatherApp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/forecast")
    fun getWeather(
        @Query("q") city: String = "Bishkek",
        @Query("appid") appid: String = "bdb2917eb8179d50d760b162dcdc2e24",
        @Query("units") units: String = "metric"
    ): Call<WeatherApp>
}