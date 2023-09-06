package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class Location(
    val country: String,
    val lat: Double,
    val localtime: String,
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Int,
    val lon: Double,
    val name: String,
    val region: String,
)