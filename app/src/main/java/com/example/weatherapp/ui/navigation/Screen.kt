package com.example.weatherapp.ui.navigation

const val LOCATION_KEY = "location_key"
sealed class Screen(val route: String) {
    object WeatherScreen: Screen("weather_screen")
    object ForecastScreen: Screen("forecast_screen/{$LOCATION_KEY}") {
        fun passLocation(data: String): String {
            return this.route.replace(
                oldValue = "{$LOCATION_KEY}",
                newValue = data
            )
        }
    }
    object SettingsScreen: Screen("settings_screen")
    object CityScreen: Screen("city_management_screen")
}
