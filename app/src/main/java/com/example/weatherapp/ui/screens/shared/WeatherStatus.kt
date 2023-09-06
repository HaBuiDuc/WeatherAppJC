package com.example.weatherapp.ui.screens.shared

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.LightMint
import com.example.weatherapp.ui.theme.PaleBlue
import com.example.weatherapp.ui.theme.SandyBeige

@Preview
@Composable
fun WeatherStatusPreview() {
}

@Composable
fun WeatherStatus(
    itemList: List<StatusItemData>,
    modifier: Modifier = Modifier,
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
//        WeatherStatusItem(
//            painter = painterResource(id = R.drawable.icon_wind),
//            weatherInformation = windSpeed,
//            modifier = itemModifier(LightMint),
//            textColor = textColor
//        )
//        WeatherStatusItem(
//            painter = painterResource(id = R.drawable.icon_water_drop),
//            weatherInformation = "${humidity}%",
//            modifier = itemModifier(PaleBlue),
//            textColor = textColor
//        )
//        WeatherStatusItem(
//            painter = painterResource(id = R.drawable.icon_sun_mini),
//            weatherInformation = "${rain}%",
//            modifier = itemModifier(SandyBeige),
//            textColor = textColor
//        )
        itemList.forEach {item ->
            WeatherStatusItem(statusItemData = item)
        }
    }
}

@Composable
fun WeatherStatusItem(
    statusItemData: StatusItemData
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(statusItemData.backgroundColor)
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = statusItemData.iconId),
                contentDescription = stringResource(id = R.string.image_des),
                modifier = Modifier

                    .size(32.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = statusItemData.weatherInformation,
            color = statusItemData.textColor
        )
    }
}

data class StatusItemData(
    val iconId: Int,
    val weatherInformation: String,
    val backgroundColor: Color,
    val textColor: Color = Color.Black
)



