package com.example.weatherapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.location.getCurrentLocation
import com.example.weatherapp.data.repository.SettingsRepository
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.ui.states.ForecastState
import com.example.weatherapp.util.getLocationValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForecastViewModel : ViewModel() {
    private val settingsRepository = SettingsRepository.get()
    private val repository = WeatherRepository()
    private val _state = MutableStateFlow(ForecastState())
    val state: StateFlow<ForecastState> = _state.asStateFlow()

    fun getForecastDate(
        context: Context,
        location: String? = null
    ) {
        if (location == null) {
            getCurrentLocation(context) {
                viewModelScope.launch {
                    _state.value = ForecastState(
                        repository.getWeatherForecast(it.getLocationValue()),
                        isInitialize = true
                    )
                }
            }
        } else {
            viewModelScope.launch {
                _state.value = ForecastState(
                    repository.getWeatherForecast(location),
                    isInitialize = true
                )
            }
        }
        Log.d(TAG, "getWeatherData: ")
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
            _state.value.settingValue[settingKey] = data
        }
    }

    companion object {
        const val TAG = "ForecastViewModel"
    }
}