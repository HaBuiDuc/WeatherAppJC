package com.example.weatherapp.ui.screens.current_screen

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.data.model.Current
import com.example.weatherapp.ui.theme.Silver
import com.example.weatherapp.ui.theme.TextBoldStyle
import com.example.weatherapp.ui.theme.TextNormalStyle
import com.example.weatherapp.util.getFeelLike
import com.example.weatherapp.util.getPressure
import com.example.weatherapp.util.getVisibility
import com.example.weatherapp.util.getWindSpeed

@Preview
@Composable
fun DetailViewPreview() {
}

@Composable
fun DetailView(
    currentWeather: Current,
    changeOfRain: Int,
    settingsValue: Map<String, Int>
) {
    val context = LocalContext.current

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    ) {
        val itemList1 = listOf(
            DetailItemData(
                iconId = R.drawable.icon_rain_mini,
                title = stringResource(id = R.string.rain),
                weatherData = "${changeOfRain}%"
            ),
            DetailItemData(
                iconId = R.drawable.icon_pressure,
                title = stringResource(id = R.string.pressure),
                weatherData = currentWeather.getPressure(
                    context = context,
                    settingsValue = settingsValue
                )
            ),
            DetailItemData(
                iconId = R.drawable.icon_sun_mini,
                title = stringResource(id = R.string.uv),
                weatherData = "${currentWeather.uv.toInt()}"
            )
        )
        DetailItemColumn(itemList = itemList1)
        val itemList2 = listOf(
            DetailItemData(
                iconId = R.drawable.icon_thermometer,
                title = stringResource(id = R.string.feels_like),
                weatherData = currentWeather.getFeelLike(
                    context = context,
                    settingsValue = settingsValue
                )
            ),
            DetailItemData(
                iconId = R.drawable.icon_wind,
                title = stringResource(id = R.string.wind),
                weatherData = currentWeather.getWindSpeed(context, settingsValue)
            ),
            DetailItemData(
                iconId = R.drawable.icon_view,
                title = stringResource(id = R.string.visibility),
                weatherData = currentWeather.getVisibility(
                    context = context,
                    settingsValue = settingsValue
                )
            )
        )
        DetailItemColumn(itemList = itemList2)
    }
}

@Composable
fun DetailItemColumn(
    itemList: List<DetailItemData>
) {
    Column {
        itemList.forEach {item ->
            DetailItem(detailItem = item)
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview
@Composable
fun DetailItemPreview() {
    DetailItem(
        DetailItemData(
            iconId = R.drawable.icon_humidity,
            title = "Humidity",
            weatherData = "69%"
        )
    )
}

@Composable
fun DetailItem(
    detailItem: DetailItemData
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = Silver,
                    shape = CircleShape
                )
                .padding(8.dp)
        ) {
            Icon(
                painter = painterResource(id = detailItem.iconId),
                contentDescription = stringResource(id = R.string.image_des),
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = detailItem.title,
                style = TextNormalStyle
            )
            Text(
                text = detailItem.weatherData,
                style = TextBoldStyle
            )
        }
    }
}

data class DetailItemData(
    val iconId: Int,
    val title: String,
    val weatherData: String
)