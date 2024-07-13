package com.example.weatherappfragment.model.roomEntity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "forecast")
data class Forecast(
    @PrimaryKey(autoGenerate = true) val forecastId: Int = 0,
    val dateTime: String,
    val temp: Double,
    val cityName: String,
    val condition: String
) {
}