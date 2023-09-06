package com.example.weatherapp.ui.screens.current_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.data.getIconWeather
import com.example.weatherapp.data.model.Current
import com.example.weatherapp.ui.screens.shared.StatusItemData
import com.example.weatherapp.ui.screens.shared.WeatherStatus
import com.example.weatherapp.ui.theme.LightMint
import com.example.weatherapp.ui.theme.PaleBlue
import com.example.weatherapp.ui.theme.SandyBeige
import com.example.weatherapp.ui.theme.TextBoldStyle
import com.example.weatherapp.ui.theme.TextNormalStyle
import com.example.weatherapp.ui.theme.TextSemiBoldStyle
import com.example.weatherapp.util.getTemp
import com.example.weatherapp.util.getWindSpeed

@Preview
@Composable
fun GeneralViewPreview() {
}

@Composable
fun GeneralView(
    modifier: Modifier = Modifier,
    currentWeather: Current,
    settingsValue: Map<String, Int>
) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp)
    ) {
        Text(
            text = stringResource(id = R.string.today),
            style = TextSemiBoldStyle,
            fontSize = 36.sp
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row {
            val conditionCode = currentWeather.condition.code
            val iconCode = getIconWeather(conditionCode, currentWeather.isDay)
            Image(
                painter = painterResource(id = iconCode),
                contentDescription = stringResource(id = R.string.image_des),
                modifier = Modifier.size(128.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                val currentTemp = currentWeather.getTemp(
                    context = context,
                    settingsValue = settingsValue
                )
                Text(
                    text = currentTemp,
                    fontSize = 56.sp,
                    style = TextBoldStyle,
                )
                Text(
                    text = currentWeather.condition.status,
                    fontSize = 20.sp,
                    style = TextNormalStyle
                )
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        val currentWindSpeed = currentWeather.getWindSpeed(
            context = context,
            settingsValue = settingsValue
        )

        val itemList = listOf(
            StatusItemData(
                iconId = R.drawable.icon_wind,
                weatherInformation = currentWindSpeed,
                backgroundColor = LightMint,
            ),
            StatusItemData(
                iconId = R.drawable.icon_humidity,
                weatherInformation = "${currentWeather.humidity}%",
                backgroundColor = PaleBlue,
            ),
            StatusItemData(
                iconId = R.drawable.icon_sun_mini,
                weatherInformation = "${currentWeather.uv.toInt()}",
                backgroundColor = SandyBeige,
            )
        )
        WeatherStatus(
            itemList
        )
    }
}