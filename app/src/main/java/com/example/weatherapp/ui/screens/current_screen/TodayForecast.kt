package com.example.weatherapp.ui.screens.current_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.data.getIconWeather
import com.example.weatherapp.data.model.Hour
import com.example.weatherapp.ui.theme.TextNormalStyle
import com.example.weatherapp.ui.theme.TextSemiBoldStyle
import com.example.weatherapp.util.getHour
import com.example.weatherapp.util.getTemp

@Composable
fun TodayForecast(
    hourList: List<Hour>,
    settingsValue: Map<String, Int>,
    onWeeklyForecastNav: () -> Unit,
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),

        ) {
            Text(
                text = stringResource(id = R.string.today),
                style = TextSemiBoldStyle,
                fontSize = 22.sp
            )
            Text(
                text = stringResource(id = R.string.next_seven_day),
                style = TextSemiBoldStyle,
                fontSize = 22.sp,
                modifier = Modifier.clickable {
                    onWeeklyForecastNav()
                }
            )
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            items(hourList) {weatherByHour ->
                TodayForecastItem(
                    weatherByHour = weatherByHour,
                    settingsValue = settingsValue
                )
            }
        }
    }
}

@Composable
private fun TodayForecastItem(
    weatherByHour: Hour,
    settingsValue: Map<String, Int>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
//            .border(
//                width = 0.4.dp,
//                color = Color.Gray,
//                shape = RoundedCornerShape(10.dp)
//            )
//            .background(BlueBrush, RoundedCornerShape(10.dp))
            .padding(
                vertical = 4.dp,
                horizontal = 8.dp
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = weatherByHour.getHour(),
                style = TextNormalStyle,
//                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))

            val conditionCode = weatherByHour.condition.code
            val iconId = getIconWeather(conditionCode, weatherByHour.isDay)
            Image(
                painter = painterResource(id = iconId),
                contentDescription = stringResource(id = R.string.image_des),
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))

            val hourTemp = weatherByHour.getTemp(
                context = context,
                settingsValue = settingsValue
            )
            Text(
                text = hourTemp,
                style = TextSemiBoldStyle,
//                color = Color.White
            )
        }
    }
}