package com.example.weatherappfragment.model.response


import com.google.gson.annotations.SerializedName


data class WeatherResponse(
    @SerializedName("address")
    val address: String = "",
    @SerializedName("alerts")
    val alerts: List<Any> = emptyList(),
    @SerializedName("currentConditions")
    val currentConditions: CurrentConditions = CurrentConditions(),
    @SerializedName("days")
    val days: List<Day> = emptyList(),
    @SerializedName("description")
    val description: String = "",
    @SerializedName("latitude")
    val latitude: Double = 0.0,
    @SerializedName("longitude")
    val longitude: Double = 0.0,
    @SerializedName("queryCost")
    val queryCost: Int = 0,
    @SerializedName("resolvedAddress")
    val resolvedAddress: String = "",
    @SerializedName("stations")
    val stations: Stations = Stations(F1417(),F1417(),KJRB(),KLGA(),KNYC(),NJ12()),
    @SerializedName("timezone")
    val timezone: String = "",
    @SerializedName("tzoffset")
    val tzoffset: Double = 0.0
)