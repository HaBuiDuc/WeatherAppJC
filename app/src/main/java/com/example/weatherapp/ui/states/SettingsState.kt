package com.example.weatherapp.ui.states

import com.example.weatherapp.data.MeasureUnit.*
import com.example.weatherapp.data.repository.SettingsRepository
import com.example.weatherapp.ui.screens.settings_screen.SettingItem

data class SettingsState(
    val settingValue: MutableMap<String, Int> = mutableMapOf(),
)
