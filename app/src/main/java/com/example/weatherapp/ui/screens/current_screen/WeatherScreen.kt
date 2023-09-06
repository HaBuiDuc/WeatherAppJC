package com.example.weatherapp.ui.screens.current_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.R
import com.example.weatherapp.ui.navigation.Screen
import com.example.weatherapp.ui.navigation.navigation_drawer.Drawer
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Preview
@Composable
fun WeatherScreenPreview() {
    WeatherScreen(navController = rememberNavController())
}

@Composable
fun WeatherScreen(
    navController: NavController,
    viewModel: WeatherViewModel = viewModel()
) {
    viewModel.getSettings(LocalContext.current)
    val weatherState by viewModel.weatherState.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    if (!weatherState.isInitialize) {
        viewModel.getWeatherData(context)
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    ModalNavigationDrawer(
        drawerContent = {
            Drawer(
                drawerState = drawerState,
                navController = navController
            )
        },
        drawerState = drawerState,
    ) {
        Scaffold(
            topBar = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_menu),
                    contentDescription = stringResource(id = R.string.image_des),
                    modifier = Modifier
                        .padding(start = 14.dp)
                        .size(40.dp)
                        .clickable {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                 )
            },

            ) { padding ->
            val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = weatherState.isLoading)
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    viewModel.getWeatherData(context)
                },
                modifier = Modifier.padding(padding)
            ) {
                Column(
                    modifier = Modifier
//                        .background(Silver)
                        .verticalScroll(rememberScrollState())
                ) {
                    if (weatherState.weatherData != null) {
                        val currentWeather = weatherState.weatherData!!.current
                        LocationView(weatherState.weatherData!!.location)
                        Spacer(modifier = Modifier.height(32.dp))
                        GeneralView(
                            modifier = Modifier,
                            currentWeather = currentWeather,
                            settingsValue = weatherState.settingValue
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        val hourList = weatherState.weatherData!!.forecast.forecastDay[0].hour
                        TodayForecast(
                            hourList = hourList,
                            settingsValue = weatherState.settingValue,
                        ) {
                            navController.navigate(Screen.ForecastScreen.route)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        val changeOfRain = weatherState.weatherData!!.forecast.forecastDay[0].day.dailyChanceOfRain
                        DetailView(
                            currentWeather = currentWeather,
                            changeOfRain = changeOfRain,
                            settingsValue = weatherState.settingValue
                        )
                    }
                }
            }
        }
    }
}
