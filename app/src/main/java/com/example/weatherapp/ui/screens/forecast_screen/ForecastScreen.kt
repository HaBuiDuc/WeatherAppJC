package com.example.weatherapp.ui.screens.forecast_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.TextBoldStyle
import com.example.weatherapp.viewmodel.ForecastViewModel

@Composable
fun ForecastScreen(
    navController: NavController,
    viewModel: ForecastViewModel = viewModel(),
) {
    val context = LocalContext.current
    viewModel.getSettings(context)
    val forecastState by viewModel.state.collectAsState()
    val settingsValue = forecastState.settingValue
    if (!forecastState.isInitialize) {
        viewModel.getForecastDate(context = context)
    }
    Scaffold(
        topBar = {
            TopBar {
                navController.popBackStack()
            }
        }
    ) {padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            if (forecastState.forecastDate != null) {
                val forecast = forecastState.forecastDate!!.forecast
                TomorrowForecast(
                    weather = forecast.forecastDay[1].day,
                    weatherDate = forecast.forecastDay[1].date,
                    settingsValue = settingsValue
                )
                WeeklyForecast(
                    weatherForecast = forecast
                )
            }
        }
    }


}

@Composable
fun TopBar(
    onBackButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_left),
            contentDescription = stringResource(id = R.string.image_des),
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.CenterStart)
                .clickable {
                    onBackButtonClick()
                }
        )
        Text(
            text = stringResource(id = R.string.next_seven_day),
            style = TextBoldStyle,
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}