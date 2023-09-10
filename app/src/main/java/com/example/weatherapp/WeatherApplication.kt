package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.data.repository.CitiesRepository
import com.example.weatherapp.data.repository.SettingsRepository
import com.example.weatherapp.util.googlePlaceApiKey
import com.google.android.libraries.places.api.Places

class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SettingsRepository.initialize(this)
        CitiesRepository.initialize()
        Places.initialize(this, googlePlaceApiKey);
    }
}