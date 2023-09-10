package com.example.weatherapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.weatherapp.data.MeasureUnit
import kotlinx.coroutines.flow.first
import kotlin.IllegalStateException

class SettingsRepository private constructor(context: Context) {
    private val preferencesDefaults = mutableMapOf(
        WIND_SPEED_UNIT to MeasureUnit.KmPerHour.unit,
        VISIBILITY_UNIT to MeasureUnit.Km.unit,
        PRESSURE_UNIT to MeasureUnit.Millibar.unit,
        TEMPERATURE_UNIT to MeasureUnit.Celsius.unit
    )

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

    suspend fun saveSetting(context: Context, data: Int, settingKey: String) {
        val dataStoreKey = intPreferencesKey(settingKey)
        context.dataStore.edit { settings ->
            settings[dataStoreKey] = data
        }
    }

    suspend fun getSetting(context: Context, settingKey: String): Int? {
        val dataStoreKey = intPreferencesKey(settingKey)
        val preferences = context.dataStore.data.first()
        return if (preferences[dataStoreKey] != null) {
            preferences[dataStoreKey]
        } else {
            preferencesDefaults[settingKey]
        }
    }

    companion object {
        const val WIND_SPEED_UNIT = "wind_sp_unit"
        const val VISIBILITY_UNIT = "visibility_unit"
        const val PRESSURE_UNIT = "pressure_unit"
        const val TEMPERATURE_UNIT = "temperature_unit"


        private var INSTANCE: SettingsRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = SettingsRepository(context)
            }
        }

        fun get(): SettingsRepository {
            return INSTANCE ?: throw IllegalStateException("repository must be init")
        }
    }
}