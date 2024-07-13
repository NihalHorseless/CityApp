package com.example.weatherappfragment.model

import com.example.weatherappfragment.model.roomEntity.Forecast
import java.util.TimeZone

data class City(
    val name: String, val latitude: Double, val longitude: Double,
    val resolvedAdress: String, val timeZone: TimeZone, val forecasts: List<Forecast>
) {
}