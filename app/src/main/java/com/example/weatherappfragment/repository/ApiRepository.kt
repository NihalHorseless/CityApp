package com.example.weatherappfragment.repository

import com.example.weatherappfragment.model.response.WeatherResponse
import com.example.weatherappfragment.network.WeatherApiService

class ApiRepository(private val api: WeatherApiService) {
    suspend fun fetchData(longtitude: Float, latitude: Float): WeatherResponse {
        return api.getData(
            longtitude = longtitude,
            latitude = latitude
        )
    }
}