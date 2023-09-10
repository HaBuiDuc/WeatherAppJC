package com.example.weatherapp.data.remote

import com.example.weatherapp.data.model.WeatherDataDaily
import com.example.weatherapp.data.model.WeatherForecast
import com.example.weatherapp.util.weatherApiKey
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    // Get 1 day forecast, this data include current weather and forecast of the day
    @GET("forecast.json?key=$weatherApiKey&days=1")
    suspend fun getWeatherData(
        @Query("q") q: String
    ): WeatherDataDaily

    // Get weekly forecast
    @GET("forecast.json?key=$weatherApiKey&days=3")
    suspend fun getWeatherForecast(
        @Query("q") q: String
    ): WeatherForecast
}