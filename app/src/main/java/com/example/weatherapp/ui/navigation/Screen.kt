package com.example.weatherapp.ui.navigation

sealed class Screen(val route: String) {
    object WeatherScreen: Screen("weather_screen")
    object ForecastScreen: Screen("forecast_screen")
    object SettingsScreen: Screen("settings_screen")
    object CityScreen: Screen("city_management_screen")
}
