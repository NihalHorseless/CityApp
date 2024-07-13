package com.example.weatherappfragment.network

import com.example.weatherappfragment.model.response.WeatherResponse
import com.example.weatherappfragment.network.RequestProperties.baseUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherApiService {

    @GET("/VisualCrossingWebServices/rest/services/timeline/{latitude},{longtitude}?key=A6PB882ULGRUXKZHKNUVG4QCJ")
    suspend fun getData(@Path("longtitude") longtitude: Float,
                        @Path("latitude") latitude: Float): WeatherResponse

}
object RequestProperties {
    val apiKey = "A6PB882ULGRUXKZHKNUVG4QCJ"
    val baseUrl = "https://weather.visualcrossing.com/"
}
object RetrofitHelper {


    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getMyApi(): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }
}
