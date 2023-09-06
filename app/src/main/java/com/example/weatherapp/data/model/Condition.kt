package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class Condition(
    val code: Int,
    val icon: String,
    @SerializedName("text")
    val status: String
)