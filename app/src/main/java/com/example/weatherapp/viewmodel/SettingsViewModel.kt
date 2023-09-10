package com.example.weatherapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.repository.SettingsRepository
import com.example.weatherapp.ui.states.SettingsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    private val settingsRepository = SettingsRepository.get()
    private val _settingsState = MutableStateFlow(SettingsState())
    val settingsState: StateFlow<SettingsState> = _settingsState.asStateFlow()

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

    fun saveSetting(context: Context, data: Int, settingKey: String) {
        viewModelScope.launch {
            settingsRepository.saveSetting(context, data, settingKey)
        }
    }

    private fun setState(data: Int?, settingKey: String) {
        if (data != null) {
            _settingsState.value.settingValue[settingKey] = data
        }
    }

}