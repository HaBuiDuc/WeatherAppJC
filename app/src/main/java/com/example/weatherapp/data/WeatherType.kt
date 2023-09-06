package com.example.weatherapp.data

import com.example.weatherapp.data.model.weather_properties.WeatherIcon.*

fun getIconWeather(id: Int, isDay: Int): Int {
    when(id) {
        1000 -> {
            return if (isDay == 1) Sun.iconId else Moon.iconId
        }
        1003 -> {
            return if (isDay == 1) CloudyWithSunny.iconId else CloudyWithMoon.iconId
        }
        1006, 1009 -> {
            return if (isDay == 1) CloudyDay.iconId else CloudyNight.iconId
        }
        1063, 1180, 1183, 1186, 1189, 1192, 1195, 1240, 1243, 1246 -> {
            return if (isDay == 1) RainyDay.iconId else RainyNight.iconId
        }
        1066, 1114, 1210, 1213, 1216, 1219, 1222, 1225, 1255, 1258, 1261, 1264 -> {
            return Snow.iconId
        }
        1069, 1204, 1207, 1237, 1249, 1252 -> {
            return Sleet.iconId
        }
        1117, 1279, 1282 -> {
            return SnowWithThunder.iconId
        }
        1273, 1276 -> {
            return if (isDay == 1) StormDay.iconId else StormNight.iconId
        }
        1168, 1171, 1198, 1201, 1672, 1150, 1153 -> {
            return Drizzle.iconId
        }
        1135, 1147, 1030 -> {
            return Haze.iconId
        }
    }
    return 0
}