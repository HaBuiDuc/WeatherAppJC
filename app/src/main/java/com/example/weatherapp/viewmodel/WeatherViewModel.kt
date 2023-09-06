package com.example.weatherapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.location.getCurrentLocation
import com.example.weatherapp.data.repository.SettingsRepository
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.ui.states.WeatherState
import com.example.weatherapp.util.getLocationValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class WeatherViewModel : ViewModel() {
    private val settingsRepository = SettingsRepository.get()
    private val repository = WeatherRepository()
    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()

    fun getWeatherData(context: Context) {
        getCurrentLocation(context) {
            viewModelScope.launch {
                _weatherState.value.isLoading = true
                _weatherState.value = WeatherState(
                    repository.getWeatherData(it.getLocationValue()),
                    isInitialize = true
                )
                _weatherState.value.isLoading = false
            }
            Log.d(TAG, "getWeatherData: ")
        }
    }

    fun getSettings(context: Context) {
        viewModelScope.launch {
            val windSpeedUnit = settingsRepository.getSetting(context, SettingsRepository.WIND_SPEED_UNIT)
            val visibilityUnit = settingsRepository.getSetting(context, SettingsRepository.VISIBILITY_UNIT)
            val pressureUnit = settingsRepository.getSetting(context, SettingsRepository.PRESSURE_UNIT)
            val temperatureUnit = settingsRepository.getSetting(context, SettingsRepository.TEMPERATURE_UNIT)
            setState(windSpeedUnit, SettingsRepository.WIND_SPEED_UNIT)
            setState(visibilityUnit, SettingsRepository.VISIBILITY_UNIT)
            setState(pressureUnit, SettingsRepository.PRESSURE_UNIT)
            setState(temperatureUnit, SettingsRepository.TEMPERATURE_UNIT)
        }
        Log.d("This is a log", "getSettings: ")
    }

    private fun setState(data: Int?, settingKey: String) {
        if (data != null) {
            _weatherState.value.settingValue[settingKey] = data
        }
    }

    companion object {
        const val TAG = "WeatherViewModel"
    }
}


