package com.example.weatherapp.ui.screens.forecast_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.data.getIconWeather
import com.example.weatherapp.data.model.Day
import com.example.weatherapp.ui.screens.shared.StatusItemData
import com.example.weatherapp.ui.screens.shared.WeatherStatus
import com.example.weatherapp.ui.theme.BlueBrush
import com.example.weatherapp.ui.theme.LightMint
import com.example.weatherapp.ui.theme.PaleBlue
import com.example.weatherapp.ui.theme.SandyBeige
import com.example.weatherapp.ui.theme.TextBoldStyle
import com.example.weatherapp.ui.theme.TextNormalStyle
import com.example.weatherapp.ui.theme.TextSemiBoldStyle
import com.example.weatherapp.util.getMaxTemp
import com.example.weatherapp.util.getMinTemp
import com.example.weatherapp.util.getWindSpeed

@Composable
fun TomorrowForecast(
    weather: Day,
    weatherDate: String,
    settingsValue: Map<String, Int>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .padding(16.dp)
            .background(BlueBrush, RoundedCornerShape(10.dp))
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Column {
            BriefInformation(weatherDate)
            Spacer(modifier = Modifier.height(16.dp))
            WeatherCondition(
                weatherDay = weather,
                settingsValue = settingsValue
            )
            Spacer(modifier = Modifier.height(24.dp))

            val itemList = listOf(
                StatusItemData(
                    iconId = R.drawable.icon_wind,
                    weatherInformation = weather.getWindSpeed(
                        context = context,
                        settingsValue = settingsValue
                    ),
                    backgroundColor = LightMint,
                ),
                StatusItemData(
                    iconId = R.drawable.icon_humidity,
                    weatherInformation = "${weather.avgHumidity.toInt()}%",
                    backgroundColor = PaleBlue,
                ),
                StatusItemData(
                    iconId = R.drawable.icon_umbrella,
                    weatherInformation = "${weather.dailyChanceOfRain}%",
                    backgroundColor = SandyBeige,
                )
            )
            WeatherStatus(
                itemList
            )
        }
    }
}

@Composable
fun BriefInformation(
    weatherDate: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.tomorrow),
            color = Color.White,
            style = TextSemiBoldStyle,
            fontSize = 24.sp
        )
        Text(
            text = weatherDate,
            color = Color.White,
            style = TextNormalStyle,
        )
    }
}

@Composable
fun WeatherCondition(
    weatherDay: Day,
    settingsValue: Map<String, Int>
) {
    val context = LocalContext.current
    Row {
        val conditionCode = weatherDay.condition.code
        val iconId = getIconWeather(conditionCode, 1)
        Image(
            painter = painterResource(id = iconId),
            contentDescription = stringResource(id = R.string.image_des),
            modifier = Modifier.size(88.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = weatherDay.getMaxTemp(
                        context = context,
                        settingsValue = settingsValue
                    ),
                    style = TextBoldStyle,
                    fontSize = 40.sp,
                    color = Color.White
                )
                Text(
                    text = " / ${weatherDay.getMinTemp(
                        context = context,
                        settingsValue = settingsValue
                    )}",
                    style = TextBoldStyle,
                    fontSize = 32.sp,
                    color = Color.White
                )
            }
            Text(
                text = weatherDay.condition.status,
                style = TextNormalStyle,
                color = Color.White
            )
        }
    }
}
