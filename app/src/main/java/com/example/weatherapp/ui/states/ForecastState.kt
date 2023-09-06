package com.example.weatherapp.ui.states

import com.example.weatherapp.data.model.WeatherForecast

data class ForecastState(
    val forecastDate: WeatherForecast? = null,
    var isInitialize: Boolean = false,
    val settingValue: MutableMap<String, Int> = mutableMapOf()
)
