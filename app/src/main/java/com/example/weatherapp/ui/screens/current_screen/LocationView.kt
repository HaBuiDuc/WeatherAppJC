package com.example.weatherapp.ui.screens.current_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.data.model.Location
import com.example.weatherapp.ui.theme.TextBoldStyle
import com.example.weatherapp.ui.theme.TextNormalStyle

@Composable
fun LocationView(
    weatherLocation: Location
) {
    Column(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
    ) {
        Text(
            text = weatherLocation.name,
            fontSize = 24.sp,
            style = TextBoldStyle
        )
        Text(
            text = weatherLocation.country,
            fontSize = 24.sp,
            style = TextBoldStyle,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = weatherLocation.localtime,
            fontSize = 16.sp,
            style = TextNormalStyle
        )
    }
}


