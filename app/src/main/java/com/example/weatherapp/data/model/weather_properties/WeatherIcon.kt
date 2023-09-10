package com.example.weatherapp.data.model.weather_properties

import androidx.annotation.StringRes
import com.example.weatherapp.R

// List of weather icon string id
enum class WeatherIcon(
    @StringRes val iconId: Int
) {
    Sun(
        iconId = R.drawable.icon_sun
    ),

    Moon(
        iconId = R.drawable.icon_moon
    ),

    CloudyWithSunny(
        iconId = R.drawable.icon_cloudy_with_sunny
    ),

    CloudyWithMoon(
        iconId = R.drawable.icon_cloudy_with_moon
    ),

    CloudyDay(
        iconId = R.drawable.icon_cloudy_day
    ),

    CloudyNight(
        iconId = R.drawable.icon_cloudy_night
    ),

    RainyDay(
        iconId = R.drawable.icon_rainy_day
    ),

    RainyNight(
        iconId = R.drawable.icon_rainy_night
    ),

    Snow(
        iconId = R.drawable.icon_snow
    ),

    Sleet(
        iconId = R.drawable.icon_sleet
    ),

    SnowWithThunder(
        iconId = R.drawable.icon_snow_with_thunder
    ),

    StormDay(
        iconId = R.drawable.icon_storm_day
    ),

    StormNight(
        iconId = R.drawable.icon_storm_night
    ),

    Drizzle(
        iconId = R.drawable.icon_drizzle
    ),

    Haze(
        iconId = R.drawable.icon_haze
    )
}