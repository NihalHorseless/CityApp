package com.example.weatherappfragment.data

import com.example.weatherappfragment.model.City
import java.util.TimeZone

object sampleData {
    val citiesListData = listOf(
        City(
            name = "New York City, USA",
            latitude = 40.7128,
            longitude = -74.0059,
            resolvedAdress = "407 Fifth Ave, New York, NY 10016, USA",
            timeZone = TimeZone.getTimeZone("America/New_York"),
            forecasts = emptyList()
        ),
        City(
            name = "Tokyo, Japan",
            latitude = 35.6895,
            longitude = 139.6917,
            resolvedAdress = "2-1-1, Marunouchi, Chiyoda-ku, Tokyo, Japan",
            timeZone = TimeZone.getTimeZone("Asia/Tokyo"),
            forecasts = emptyList()
        ),
        City(
            name = "Paris, France",
            latitude = 48.8566,
            longitude = 2.3522,
            resolvedAdress = "75001 Paris, France",
            timeZone = TimeZone.getTimeZone("Europe/Paris"),
            forecasts = emptyList()
        ),
        City(
            name = "London, United Kingdom",
            latitude = 51.5074,
            longitude = -0.1278,
            resolvedAdress = "City of London, UK",
            timeZone = TimeZone.getTimeZone("Europe/London"),
            forecasts = emptyList()
        ),
        City(
            name = "São Paulo, Brazil",
            latitude = -23.5505,
            longitude = -46.6333,
            resolvedAdress = "Avenida Paulista, São Paulo, Brazil",
            timeZone = TimeZone.getTimeZone("America/Sao_Paulo"),
            forecasts = emptyList()
        ),
        City(
            name = "Berlin, Germany",
            latitude = 52.5200,
            longitude = 13.4050,
            resolvedAdress = "10117 Berlin, Germany",
            timeZone = TimeZone.getTimeZone("Europe/Berlin"),
            forecasts = emptyList()
        ),
        City(
            name = "Moscow, Russia",
            latitude = 55.7558,
            longitude = 37.6173,
            resolvedAdress = "Ulitsa Tverskaya, 1, Moscow, Russia",
            timeZone = TimeZone.getTimeZone("Europe/Moscow"),
            forecasts = emptyList()
        ),
        City(
            name = "Delhi, India",
            latitude = 28.6139,
            longitude = 77.2088,
            resolvedAdress = "New Delhi, India",
            timeZone = TimeZone.getTimeZone("Asia/Calcutta"),
            forecasts = emptyList()
        ),
        City(
            name = "Sydney, Australia",
            latitude = -33.8688,
            longitude = 151.2093,
            resolvedAdress = "Sydney, New South Wales, Australia",
            timeZone = TimeZone.getTimeZone("Australia/Sydney"),
            forecasts = emptyList()
        ),
        City(
            name = "Buenos Aires, Argentina",
            latitude = -34.8580,
            longitude = -56.1770,
            resolvedAdress = "CABA, Argentina",
            timeZone = TimeZone.getTimeZone("America/Argentina/Buenos_Aires"),
            forecasts = emptyList()
        )
    )
}