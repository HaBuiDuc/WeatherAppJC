package com.example.weatherapp.data.model

data class WeatherDataDaily(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)