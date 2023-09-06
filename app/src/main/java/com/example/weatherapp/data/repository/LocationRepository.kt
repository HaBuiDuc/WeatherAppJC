package com.example.weatherapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class LocationRepository private constructor(context: Context){
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "location_list")
    private val dataStoreKey = stringSetPreferencesKey(LOCATION_KEY)

    suspend fun saveLocation(context: Context, data: String) {
        val locationSet = getLocations(context).toMutableSet()
        val isAdd = locationSet.add(data)
        if (isAdd) {
            context.dataStore.edit { preference ->
                preference[dataStoreKey] = locationSet
            }
        }
    }

    suspend fun getLocations(context: Context): Set<String> {
        val preferences = context.dataStore.data.first()
        if (preferences[dataStoreKey] != null) {
            return preferences[dataStoreKey]!!
        }
        return emptySet()
    }

    companion object {
        const val LOCATION_KEY = "location_key"

        private var INSTANCE: LocationRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = LocationRepository(context)
            }
        }

        fun get(): LocationRepository {
            return INSTANCE ?: throw IllegalStateException("location repository must be init")
        }
    }
}