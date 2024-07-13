package com.example.weatherappfragment.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.weatherappfragment.model.roomEntity.Favorites
import com.example.weatherappfragment.model.roomEntity.Forecast
import java.util.Date

@Dao
interface CityDao {
    @Upsert
    suspend fun insert(city: Favorites)

    @Update
    suspend fun update(city: Favorites)

    @Delete
    suspend fun delete(city: Favorites)

    @Query("SELECT * FROM fav_cities")
    suspend fun getCities():List<Favorites>

    // Check if a city is liked or not
    @Query("SELECT EXISTS (SELECT 1 FROM fav_cities WHERE cityName = :cityName)")
    suspend fun isCityLiked(cityName: String): Boolean

    @Query("DELETE FROM fav_cities WHERE cityName = :cityName")
    suspend fun deleteByCityName(cityName: String)

    @Upsert
    suspend fun insertForecast(forecast: Forecast)

    @Query("DELETE FROM forecast WHERE cityName = :cityName")
    suspend fun deleteForecast(cityName: String)

    @Query("SELECT * FROM forecast WHERE cityName = :cityName")
    suspend fun getForecast(cityName: String): List<Forecast>

    // Check if a city is liked or not
    @Query("SELECT EXISTS (SELECT 1 FROM forecast WHERE cityName = :cityName AND dateTime = :logDate)")
    suspend fun forecastExists(cityName: String,logDate: String): Boolean



}