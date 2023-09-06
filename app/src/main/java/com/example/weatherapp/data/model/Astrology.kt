package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class Astrology(
    @SerializedName("is_moon_up")
    val isMoonUp: Int,
    @SerializedName("is_sun_up")
    val isSunUp: Int,
    @SerializedName("moonrise")
    val moonRise: String,
    @SerializedName("moonset")
    val moonSet: String,
    @SerializedName("sunrise")
    val sunRise: String,
    @SerializedName("sunset")
    val sunSet: String
)