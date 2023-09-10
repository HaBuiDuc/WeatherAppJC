package com.example.weatherapp.ui.screens.forecast_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.data.getWeatherIcon
import com.example.weatherapp.data.model.Forecast
import com.example.weatherapp.data.model.ForecastDay
import com.example.weatherapp.ui.theme.SteelBlue
import com.example.weatherapp.ui.theme.TextBoldStyle

@Composable
fun WeeklyForecast(
    weatherForecast: Forecast,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(all = 24.dp)
    ) {
        val forecastDay = weatherForecast.forecastDay
        items(forecastDay.subList(2, forecastDay.size)) {data ->
            WeeklyItem(forecastDay = data)
        }
    }
}

@Composable
fun WeeklyItem(
    forecastDay: ForecastDay
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = forecastDay.date,
            style = TextBoldStyle,
            fontSize = 24.sp,
            color = SteelBlue
        )
        Row {
            Text(
                text = forecastDay.day.maxTempCelsius.toInt().toString(),
                style = TextBoldStyle,
                fontSize = 24.sp,
                color = SteelBlue
            )
            Text(
                text = " / ${forecastDay.day.minTempsCelsius.toInt()}\u00b0",
                style = TextBoldStyle,
                fontSize = 24.sp,
                color = SteelBlue
            )
        }
        Row {
            val conditionCode = forecastDay.day.condition.code
            val iconId = getWeatherIcon(conditionCode, 1)
            Image(
                painter = painterResource(id = iconId),
                contentDescription = stringResource(id = R.string.image_des),
                modifier = Modifier.size(40.dp)
            )
        }
    }
}
