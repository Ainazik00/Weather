package com.example.weather.model

data class WeatherModel(
    val list: List<WeatherInfo>,
    val city: City
)

data class City(
    val name: String
)

data class WeatherInfo(
    val dt: Int,
    val main: Main,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val temp_max: Double,
    val temp_min: Double
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Wind(
    val speed: Double
)

data class Sys(
    val sunrise: Int,
    val sunset: Int
)
