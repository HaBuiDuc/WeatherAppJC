package com.example.weatherapp.data.model.weather_properties

import androidx.annotation.StringRes
import com.example.weatherapp.R

// List of weather status icon for detail information
enum class WeatherInfo(
    val field: Int,
    val iconId: Int
) {
    Rain(
        field = R.string.rain,
        iconId = R.drawable.icon_rain_mini
    ),

    Pressure(
        field = R.string.pressure,
        iconId = R.drawable.icon_pressure
    ),

    UV(
        field = R.string.uv,
        iconId = R.drawable.icon_sun_mini
    ),

    Humidity(
        field = R.string.humidity,
        iconId = R.drawable.icon_humidity
    ),

    Wind(
        field = R.string.wind,
        iconId = R.drawable.icon_wind
    ),

    Visibility(
        field = R.string.visibility,
        iconId = R.drawable.icon_view
    )
}