package com.example.weatherapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherapp.ui.screens.city_management_screen.CityScreen
import com.example.weatherapp.ui.screens.current_screen.WeatherScreen
import com.example.weatherapp.ui.screens.forecast_screen.ForecastScreen
import com.example.weatherapp.ui.screens.settings_screen.SettingScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.WeatherScreen.route
    ) {
        composable(
            route = Screen.WeatherScreen.route
        ) {
            WeatherScreen(navController)
        }
        composable(
            route = Screen.ForecastScreen.route
        ) {
            ForecastScreen(navController)
        }
        composable(
            route = Screen.SettingsScreen.route
        ) {
            SettingScreen(navController = navController)
        }
        composable(
            route = Screen.CityScreen.route
        ) {
            CityScreen(navController = navController)
        }
    }
}