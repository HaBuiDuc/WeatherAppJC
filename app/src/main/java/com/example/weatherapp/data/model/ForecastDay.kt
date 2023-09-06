package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class ForecastDay(
    @SerializedName("astro")
    val astrology: Astrology,
    val date: String,
    val day: Day,
    val hour: List<Hour>,
    @SerializedName("date_epoch")
    val dateEpoch: Int,
)