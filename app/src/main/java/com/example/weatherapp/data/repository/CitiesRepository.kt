package com.example.weatherapp.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.weatherapp.viewmodel.CitiesViewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.Normalizer

class CitiesRepository private constructor() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "location_list")
    private val dataStoreKey = stringSetPreferencesKey(LOCATION_KEY)

    private val token: AutocompleteSessionToken = AutocompleteSessionToken.newInstance()

    // Get prediction of place, use for location search
    fun getPrediction(
        context: Context,
        query: String,
        onLocationPrediction: (FindAutocompletePredictionsResponse) -> Unit
    ) {
        val request =
            FindAutocompletePredictionsRequest.builder()
                .setTypesFilter(listOf(PlaceTypes.CITIES)) // Place type return
                .setSessionToken(token)
                .setQuery(query)
                .build()
        val placesClient = Places.createClient(context)
        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
                onLocationPrediction(response)
            }.addOnFailureListener { exception: Exception? ->
                if (exception is ApiException) {
                    Log.e(CitiesViewModel.TAG, "Place not found: ${exception.statusCode}")
                }
            }
    }

    // Save location to location list datastore
    suspend fun saveLocation(context: Context, data: String) {
        // Convert accent letter to normal letter
        val newData = Normalizer
            .normalize(data, Normalizer.Form.NFD)
            .replace("\\p{M}".toRegex(), "")
        context.dataStore.edit { preference ->
            preference[dataStoreKey] = (preference[dataStoreKey] ?: emptySet()) + newData
        }
    }

    // Delete location from location list datastore
    suspend fun deleteLocation(context: Context, data: String) {
        context.dataStore.edit { preference ->
            preference[dataStoreKey] = (preference[dataStoreKey] ?: emptySet()) - data
        }
    }

    // Get location list from location datastore
    fun getLocations(context: Context): Flow<Set<String>> {
        return context.dataStore.data.map { it[dataStoreKey] ?: emptySet() }
    }

    companion object {
        // Key of datastore
        private const val LOCATION_KEY = "location_key"

        // Create a singleton class
        private var INSTANCE: CitiesRepository? = null

        fun initialize() {
            if (INSTANCE == null) {
                INSTANCE = CitiesRepository()
            }
        }

        fun get(): CitiesRepository {
            return INSTANCE ?: throw IllegalStateException("location repository must be init")
        }
    }
}