
package com.example.weatherapp.ui.screens.current_screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.weatherapp.util.getLocation
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Preview
@Composable
fun WeatherScreenPreview() {
    WeatherScreen(navController = rememberNavController())
}

@OptIn(ExperimentalFoundationApi::class)
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
    val pagerState = rememberPagerState()
    var selectedItemPager by remember {
        mutableStateOf(0)
    }
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
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_menu),
                        contentDescription = stringResource(id = R.string.image_des),
                        modifier = Modifier
                            .padding(start = 14.dp)
                            .size(40.dp)
                            .align(Alignment.CenterStart)
                            .clickable {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                    )
                    if (weatherState.weatherData.size > 1) {
                        Row(
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            Log.d("This is a log", "dot: ")
                            repeat(weatherState.weatherData.size) {iteration ->
                                val color = if (selectedItemPager == iteration) {
                                    Color.Black
                                } else {
                                    Color.Gray
                                }
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = color,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .size(width = 10.dp, height = 4.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                            }
                        }
                    }
                }
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
                HorizontalPager(
                    pageCount = weatherState.weatherData.size,
                    state = pagerState

                ) {
                    LaunchedEffect(pagerState) {
                        snapshotFlow { pagerState.currentPage }.collect { page ->
                            selectedItemPager = page
                        }
                    }
                    Column(
                        modifier = Modifier
//                        .background(Silver)
                            .verticalScroll(rememberScrollState())
                    ) {
                        val currentWeather = weatherState.weatherData[it].current
                        LocationView(weatherState.weatherData[it].location)
                        Spacer(modifier = Modifier.height(32.dp))
                        GeneralView(
                            modifier = Modifier,
                            currentWeather = currentWeather,
                            settingsValue = weatherState.settingValue
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        val hourList = weatherState.weatherData[it].forecast.forecastDay[0].hour
                        TodayForecast(
                            hourList = hourList,
                            settingsValue = weatherState.settingValue,
                        ) {
                            navController.navigate(
                                Screen.ForecastScreen.passLocation(weatherState.weatherData[it].getLocation())
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        val changeOfRain = weatherState.weatherData[it].forecast.forecastDay[0].day.dailyChanceOfRain
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
