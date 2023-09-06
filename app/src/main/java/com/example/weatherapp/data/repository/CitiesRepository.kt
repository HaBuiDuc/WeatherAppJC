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
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import kotlinx.coroutines.flow.first

class CitiesRepository private constructor() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "location_list")
    private val dataStoreKey = stringSetPreferencesKey(LOCATION_KEY)

    private val token: AutocompleteSessionToken = AutocompleteSessionToken.newInstance()

    // Create a RectangularBounds object.
    private val bounds: RectangularBounds = RectangularBounds.newInstance(
        LatLng(-33.880490, 151.184363),
        LatLng(-33.858754, 151.229596)
    )

    fun getPrediction(
        context: Context,
        query: String,
        onLocationPrediction: (FindAutocompletePredictionsResponse) -> Unit
    ) {
        Log.d("This is a log", query)
        val request =
            FindAutocompletePredictionsRequest.builder()
                // Call either setLocationBias() OR setLocationRestriction().
                .setLocationBias(bounds)
                //.setLocationRestriction(bounds)
                .setOrigin(LatLng(-33.8749937, 151.2041382))
                .setCountries("AU", "NZ")
                .setTypesFilter(listOf(PlaceTypes.CITIES))
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
    // Use the builder to create a FindAutocompletePredictionsRequest.

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
        private const val LOCATION_KEY = "location_key"


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