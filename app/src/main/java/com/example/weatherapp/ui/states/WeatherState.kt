package com.example.weatherapp.ui.states

import com.example.weatherapp.data.model.WeatherDataDaily

data class WeatherState(
    var weatherData: List<WeatherDataDaily> = listOf(),
    var isLoading: Boolean = false,
    var isInitialize: Boolean = false,
    val settingValue: MutableMap<String, Int> = mutableMapOf(),
)