package com.example.weatherapp.data.location

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

// Check access location permission
fun checkLocationPermission(context: Context): Boolean {
    return if (ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            1
        )
        false
    } else {
        true
    }
}

// Get current location listener
fun getCurrentLocation(context: Context, onGetLocationSuccess: (Location) -> Unit) {
    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    checkLocationPermission(context)
    fusedLocationProviderClient.lastLocation.addOnSuccessListener {currentLocation ->
        if (currentLocation != null) {
            onGetLocationSuccess(currentLocation)
        } else {
            Log.d("WeatherViewModel", "null: ")
        }
    }
}