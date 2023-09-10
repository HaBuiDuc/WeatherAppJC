package com.example.weatherapp.viewmodel


import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.repository.CitiesRepository
import com.example.weatherapp.ui.states.CitiesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CitiesViewModel : ViewModel() {
    private val citiesRepository = CitiesRepository.get()
    private val _citiesState = MutableStateFlow(CitiesState())
    val citiesState: StateFlow<CitiesState> = _citiesState.asStateFlow()

    fun addLocation(context: Context, data: String) {
        viewModelScope.launch {
            citiesRepository.saveLocation(context, data)
        }
    }
    
    fun getLocations(context: Context) {
        viewModelScope.launch { 
            citiesRepository.getLocations(context).collect {locations ->
                _citiesState.value = _citiesState.value.copy(
                    locationList = locations.toMutableList()
                )
            }
        }
        Log.d(TAG, "getLocations: ")
    }

    fun deleteLocation(context: Context, data: String) {
        viewModelScope.launch {
            citiesRepository.deleteLocation(context, data)
        }
    }

    fun getLocationPrediction(
        context: Context,
        query: String
    ) {
        citiesRepository.getPrediction(
            context = context,
            query = query
        ) { response ->
            _citiesState.value = _citiesState.value.copy(suggestionItems = response.autocompletePredictions)
            for (prediction in response.autocompletePredictions) {
                Log.i(TAG, prediction.placeId)
                Log.i(TAG, prediction.getPrimaryText(null).toString())
                Log.i(TAG, prediction.getSecondaryText(null).toString())

            }
        }
    }

    fun onQueryChange(query: String) {
        _citiesState.value = _citiesState.value.copy(locationQuery = query)
    }
    companion object {
        const val TAG = "CitiesViewModel"
    }
}