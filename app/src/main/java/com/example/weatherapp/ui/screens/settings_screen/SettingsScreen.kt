package com.example.weatherapp.ui.screens.settings_screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weatherapp.R
import com.example.weatherapp.data.MeasureUnit.Celsius
import com.example.weatherapp.data.MeasureUnit.Fahrenheit
import com.example.weatherapp.data.MeasureUnit.Inch
import com.example.weatherapp.data.MeasureUnit.Km
import com.example.weatherapp.data.MeasureUnit.KmPerHour
import com.example.weatherapp.data.MeasureUnit.Mile
import com.example.weatherapp.data.MeasureUnit.MilePerHour
import com.example.weatherapp.data.MeasureUnit.Millibar
import com.example.weatherapp.data.repository.SettingsRepository
import com.example.weatherapp.ui.screens.shared.SimpleTopBar
import com.example.weatherapp.ui.theme.TextBoldStyle
import com.example.weatherapp.ui.theme.TextNormalStyle
import com.example.weatherapp.viewmodel.SettingsViewModel

@Preview
@Composable
fun SettingScreenPreview() {
}

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    settingsViewModel: SettingsViewModel = viewModel(),
) {
    val context = LocalContext.current
    val tempUnitTitle = stringResource(id = R.string.temperature_unit)
    val windSpeedTitle = stringResource(id = R.string.wind_speed_unit)
    val pressureTitle = stringResource(id = R.string.pressure_unit)
    val visibilityTitle = stringResource(id = R.string.visibility)
    val settingsState by settingsViewModel.settingsState.collectAsState()
    settingsViewModel.getSettings(LocalContext.current)
    val settingList = listOf(
        SettingItem(
            title = tempUnitTitle,
            valueList = listOf(
                Celsius.unit,
                Fahrenheit.unit
            )
        ),
        SettingItem(
            title = windSpeedTitle,
            valueList = listOf(
                KmPerHour.unit,
                MilePerHour.unit
            )
        ),
        SettingItem(
            pressureTitle,
            valueList = listOf(
                Millibar.unit,
                Inch.unit
            )
        ),
        SettingItem(
            visibilityTitle,
            valueList = listOf(
                Km.unit,
                Mile.unit
            )
        )
    )
    
    Scaffold(
        topBar = {
            SimpleTopBar {
                navController.popBackStack()
            }
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(all = 16.dp)
                .padding(padding)
        ) {
            Text(
                text = stringResource(id = R.string.setting),
                style = TextNormalStyle,
                fontSize = 32.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            settingList.forEach { setting ->
                SettingItemView(
                    settingsState.settingValue,
                    settingItem = setting
                ) { title, value ->
                    when(title) {
                        tempUnitTitle -> {
                            settingsState.settingValue[SettingsRepository.TEMPERATURE_UNIT] = value
                            settingsViewModel.saveSetting(context, value, SettingsRepository.TEMPERATURE_UNIT)
                        }
                        windSpeedTitle -> {
                            settingsState.settingValue[SettingsRepository.WIND_SPEED_UNIT] = value
                            settingsViewModel.saveSetting(context, value, SettingsRepository.WIND_SPEED_UNIT)
                        }
                        pressureTitle -> {
                            settingsState.settingValue[SettingsRepository.PRESSURE_UNIT] = value
                            settingsViewModel.saveSetting(context, value, SettingsRepository.PRESSURE_UNIT)
                        }
                        visibilityTitle -> {
                            settingsState.settingValue[SettingsRepository.VISIBILITY_UNIT] = value
                            settingsViewModel.saveSetting(context, value, SettingsRepository.VISIBILITY_UNIT)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SettingItemView(
    settingValue: Map<String, Int>,
    settingItem: SettingItem,
    modifier: Modifier = Modifier,
    onSettingChange: (title: String, value: Int) -> Unit,
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                isExpanded = !isExpanded
            }
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = settingItem.title,
            style = TextBoldStyle,
            fontSize = 18.sp,
        )
        Column {
            Row {
                val id = when(settingItem.title) {
                    stringResource(id = R.string.temperature_unit) -> {
                        settingValue[SettingsRepository.TEMPERATURE_UNIT]
                    }
                    stringResource(id = R.string.wind_speed_unit) -> {
                        settingValue[SettingsRepository.WIND_SPEED_UNIT]
                    }
                    stringResource(id = R.string.pressure_unit) -> {
                        settingValue[SettingsRepository.PRESSURE_UNIT]
                    }
                    stringResource(id = R.string.visibility) -> {
                        settingValue[SettingsRepository.VISIBILITY_UNIT]
                    }
                    else -> {
                        260903
                    }
                }
                if (id != null) {
                    Text(text = stringResource(id = id))
//                    Text(text = stringResource(id = settingValue[SettingsRepository.WIND_SPEED_UNIT]!!))
//                    if (settingValue[SettingsRepository.WIND_SPEED_UNIT] == R.string.mph || settingValue[SettingsRepository.WIND_SPEED_UNIT] == R.string.kmh) {
//                        Log.d("This is a log", "Equal ")
//                    } else {
//                        Log.d("This is a log", "id kmh: ${R.string.kmh}")
//                        Log.d("This is a log", "id mph: ${R.string.mph}")
//                        Log.d("This is a log", "setting value: ${settingValue[SettingsRepository.WIND_SPEED_UNIT]}")
//                        Log.d("This is a log", "not Equal ")
//                    }
//                    Text(text = stringResource(id = R.string.mph))
                }
                Icon(
                    painter = painterResource(id = R.drawable.icon_unfold),
                    contentDescription = stringResource(id = R.string.image_des)
                )
            }
            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { }
            ) {
                settingItem.valueList.forEach { value ->
                    DropdownMenuItem(
                        text = {
                            Text(text = stringResource(id = value))
                        },
                        onClick = {
                            onSettingChange(settingItem.title, value)
                            isExpanded = !isExpanded
                        },
//                        modifier = Modifier.background(Color.Black)
                    )
                }
            }
        }
    }
}
