package com.example.weatherapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.location.getCurrentLocation
import com.example.weatherapp.data.model.WeatherDataDaily
import com.example.weatherapp.data.repository.CitiesRepository
import com.example.weatherapp.data.repository.SettingsRepository
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.ui.states.WeatherState
import com.example.weatherapp.util.getLocationValue
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val citiesRepository = CitiesRepository.get()
    private val settingsRepository = SettingsRepository.get()
    private val repository = WeatherRepository()
    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()

    fun getWeatherData(context: Context) {
        getCurrentLocation(context) {currentLocation ->
            val weatherData = mutableListOf<Deferred<WeatherDataDaily>>()
            _weatherState.value.isLoading = true
            viewModelScope.launch {
                citiesRepository.getLocations(context).collect {
                    val locationList = it.toMutableList()
                    locationList.add(0, currentLocation.getLocationValue())
                    if (weatherData.size < locationList.size) {
                        for (index in weatherData.size until locationList.size) {
                            val value = locationList[index]
                            val data = async {
                                repository.getWeatherData(value)
                            }
                            weatherData.add(data)
                        }
                        _weatherState.value = _weatherState.value.copy(
                            weatherData = weatherData.awaitAll(),
                            isInitialize = true
                        )
                    } else if (weatherData.size > locationList.size){
                        weatherData.clear()
                        for (index in 0 until locationList.size) {
                            val value = locationList[index]
                            val data = async {
                                repository.getWeatherData(value)
                            }
                            weatherData.add(data)
                        }
                        _weatherState.value = _weatherState.value.copy(
                            weatherData = weatherData.awaitAll()
                        )
                    }
                }
            }
            _weatherState.value.isLoading = false
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


