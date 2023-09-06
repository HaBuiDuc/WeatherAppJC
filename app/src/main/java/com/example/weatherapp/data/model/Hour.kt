package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class Hour(
    @SerializedName("chance_of_rain")
    val changeOfRain: Int,
    val cloud: Int,
    val condition: Condition,
    @SerializedName("feelslike_c")
    val feelsLikeCelsius: Double,
    @SerializedName("feelslike_f")
    val feelsLikeFeh: Double,
    val humidity: Int,
    @SerializedName("is_day")
    val isDay: Int,
    @SerializedName("pressure_mb")
    val pressureMb: Double,
    @SerializedName("temp_c")
    val tempCelsius: Double,
    @SerializedName("temp_f")
    val tempFeh: Double,
    val time: String,
    val uv: Double,
    @SerializedName("vis_km")
    val visibilityKm: Double,
    @SerializedName("vis_miles")
    val visibilityMiles: Double,
    @SerializedName("will_it_rain")
    val willItRain: Int,
    @SerializedName("wind_dir")
    val windDirection: String,
    @SerializedName("wind_kph")
    val windKph: Double,
    @SerializedName("wind_mph")
    val windMph: Double,
)