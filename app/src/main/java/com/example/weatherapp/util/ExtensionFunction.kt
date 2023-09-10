package com.example.weatherapp.util

import android.content.Context
import android.location.Location
import com.example.weatherapp.data.MeasureUnit
import com.example.weatherapp.data.model.Current
import com.example.weatherapp.data.model.Day
import com.example.weatherapp.data.model.Hour
import com.example.weatherapp.data.model.WeatherDataDaily
import com.example.weatherapp.data.repository.SettingsRepository

fun Hour.getHour(): String {
    return this.time.substringAfter(" ")
}

fun Location.getLocationValue(): String {
    return "${this.latitude},${this.longitude}"
}

fun Current.getTemp(
    context: Context,
    settingsValue: Map<String, Int>
): String {
    return if (settingsValue[SettingsRepository.TEMPERATURE_UNIT] == MeasureUnit.Celsius.unit) {
        "${this.tempCelsius.toInt()}" + context.getString(MeasureUnit.Celsius.unit)
    } else {
        "${this.tempFeh.toInt()}" + context.getString(MeasureUnit.Fahrenheit.unit)
    }
}

fun Current.getWindSpeed(
    context: Context,
    settingsValue: Map<String, Int>
): String {
    return if (settingsValue[SettingsRepository.WIND_SPEED_UNIT] == MeasureUnit.KmPerHour.unit) {
        "${this.windKph.toInt()}" + context.getString(MeasureUnit.KmPerHour.unit)
    } else {
        "${this.windMph.toInt()}" + context.getString(MeasureUnit.MilePerHour.unit)
    }
}

fun Current.getPressure(
    context: Context,
    settingsValue: Map<String, Int>
): String {
    return if (settingsValue[SettingsRepository.PRESSURE_UNIT] == MeasureUnit.Millibar.unit) {
        "${this.pressureMb.toInt()}" + context.getString(MeasureUnit.Millibar.unit)
    } else {
        "${this.pressureIn.toInt()}" + context.getString(MeasureUnit.Inch.unit)
    }
}

fun Current.getVisibility(
    context: Context,
    settingsValue: Map<String, Int>
): String {
    return if (settingsValue[SettingsRepository.VISIBILITY_UNIT] == MeasureUnit.Km.unit) {
        "${this.visibilityKm.toInt()}" + context.getString(MeasureUnit.Km.unit)
    } else {
        "${this.visibilityMiles.toInt()}" + context.getString(MeasureUnit.Mile.unit)
    }
}

fun Current.getFeelLike(
    context: Context,
    settingsValue: Map<String, Int>
): String {
    return if (settingsValue[SettingsRepository.TEMPERATURE_UNIT] == MeasureUnit.Celsius.unit) {
        "${this.feelsLikeCelsius.toInt()}" + context.getString(MeasureUnit.Celsius.unit)
    } else {
        "${this.feelsLikeFeh.toInt()}" + context.getString(MeasureUnit.Fahrenheit.unit)
    }
}

fun Hour.getTemp(
    context: Context,
    settingsValue: Map<String, Int>
): String {
    return if (settingsValue[SettingsRepository.TEMPERATURE_UNIT] == MeasureUnit.Celsius.unit) {
        "${this.tempCelsius.toInt()}" + context.getString(MeasureUnit.Celsius.unit)
    } else {
        "${this.tempFeh.toInt()}" + context.getString(MeasureUnit.Fahrenheit.unit)
    }
}

fun Day.getMaxTemp(
    context: Context,
    settingsValue: Map<String, Int>
): String {
    return if (settingsValue[SettingsRepository.TEMPERATURE_UNIT] == MeasureUnit.Celsius.unit) {
        "${this.maxTempCelsius.toInt()}"
    } else {
        "${this.maxTempFeh.toInt()}"
    }
}

fun Day.getMinTemp(
    context: Context,
    settingsValue: Map<String, Int>
): String {
    return if (settingsValue[SettingsRepository.TEMPERATURE_UNIT] == MeasureUnit.Celsius.unit) {
        "${this.minTempsCelsius.toInt()}" + context.getString(MeasureUnit.Celsius.unit)
    } else {
        "${this.minTempFeh.toInt()}" + context.getString(MeasureUnit.Fahrenheit.unit)
    }
}

fun Day.getWindSpeed(
    context: Context,
    settingsValue: Map<String, Int>
): String {
    return if (settingsValue[SettingsRepository.WIND_SPEED_UNIT] == MeasureUnit.KmPerHour.unit) {
        "${this.maxWindKph.toInt()}" + context.getString(MeasureUnit.KmPerHour.unit)
    } else {
        "${this.maxWindMph.toInt()}" + context.getString(MeasureUnit.MilePerHour.unit)
    }
}

fun WeatherDataDaily.getLocation(): String {
    return "${this.location.name},${this.location.country}"
}