package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("avghumidity")
    val avgHumidity: Double,
    @SerializedName("avgtemp_c")
    val avgTempCelsius: Double,
    @SerializedName("avgtemp_f")
    val avgTempFeh: Double,
    val condition: Condition,
    @SerializedName("daily_chance_of_rain")
    val dailyChanceOfRain: Int,
    @SerializedName("daily_chance_of_snow")
    val dailyChanceOfSnow: Int,
    @SerializedName("daily_will_it_rain")
    val dailyWillItRain: Int,
    @SerializedName("daily_will_it_snow")
    val dailyWillItSnow: Int,
    @SerializedName("maxtemp_c")
    val maxTempCelsius: Double,
    @SerializedName("maxtemp_f")
    val maxTempFeh: Double,
    @SerializedName("maxwind_kph")
    val maxWindKph: Double,
    @SerializedName("maxwind_mph")
    val maxWindMph: Double,
    @SerializedName("mintemp_c")
    val minTempsCelsius: Double,
    @SerializedName("mintemp_f")
    val minTempFeh: Double,
    val uv: Double
)