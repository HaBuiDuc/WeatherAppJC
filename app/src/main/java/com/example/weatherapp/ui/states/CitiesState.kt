package com.example.weatherapp.ui.states

import com.google.android.libraries.places.api.model.AutocompletePrediction

data class CitiesState(
    var locationQuery: String = "",
    val suggestionItems: List<AutocompletePrediction> = mutableListOf(),
    val locationList: List<String> = mutableListOf()
)