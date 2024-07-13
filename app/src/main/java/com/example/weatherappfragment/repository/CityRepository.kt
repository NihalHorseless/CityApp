package com.example.weatherappfragment.repository

import com.example.weatherappfragment.data.database.CityDatabase
import com.example.weatherappfragment.model.roomEntity.Favorites
import com.example.weatherappfragment.model.roomEntity.Forecast
import java.util.Date

class CityRepository(private val cityDatabase: CityDatabase) {

    suspend fun getCities(): List<Favorites> = cityDatabase.cityDao().getCities()

    suspend fun insert(city: Favorites) = cityDatabase.cityDao().insert(city)

    suspend fun isCityLiked(cityId: String): Boolean = cityDatabase.cityDao().isCityLiked(cityId)

    suspend fun update(city: Favorites) = cityDatabase.cityDao().update(city)

    suspend fun delete(city: Favorites) = cityDatabase.cityDao().delete(city)

    suspend fun deleteByCityName(cityName: String) =
        cityDatabase.cityDao().deleteByCityName(cityName)

    suspend fun insertForecast(forecast: Forecast) = cityDatabase.cityDao().insertForecast(forecast)

    suspend fun deleteForecast(cityName: String) = cityDatabase.cityDao().deleteForecast(cityName)

    suspend fun getForecast(cityName: String): List<Forecast> =
        cityDatabase.cityDao().getForecast(cityName)

    suspend fun forecastExists(cityName: String, logDate: String): Boolean =
        cityDatabase.cityDao().forecastExists(cityName = cityName, logDate = logDate)


}