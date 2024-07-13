package com.example.weatherappfragment.model.roomEntity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "fav_cities")
data class Favorites(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val cityName: String,
    val cityLongitude: Float,
    val cityLatitude: Float
)