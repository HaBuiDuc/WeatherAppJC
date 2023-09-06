package com.example.weatherapp.data

import androidx.annotation.StringRes
import com.example.weatherapp.R

enum class MeasureUnit(
    @StringRes val unit: Int
) {
    KmPerHour(
        unit = R.string.kmh
    ),
    MilePerHour(
        unit = R.string.mph
    ),
    Km(
        unit = R.string.km
    ),
    Mile(
        unit = R.string.mile
    ),
    Celsius(
        unit = R.string.celsius
    ),
    Fahrenheit(
        unit = R.string.fahrenheit
    ),
    Millibar(
        unit = R.string.mb
    ),
    Inch(
        unit = R.string.inch
    )
}