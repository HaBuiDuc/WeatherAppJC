package com.example.weatherapp.data.repository

import com.example.weatherapp.data.model.WeatherDataDaily
import com.example.weatherapp.data.model.WeatherForecast
import com.example.weatherapp.data.remote.RetrofitInstance

class WeatherRepository {
    suspend fun getWeatherData(q: String): WeatherDataDaily =
        RetrofitInstance.api.getWeatherData(q)

    suspend fun getWeatherForecast(q: String): WeatherForecast =
        RetrofitInstance.api.getWeatherForecast(q)
}