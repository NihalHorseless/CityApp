package com.example.weatherappfragment.model.response


import com.google.gson.annotations.SerializedName

data class F1417(
    @SerializedName("contribution")
    val contribution: Double = 0.0,
    @SerializedName("distance")
    val distance: Double = 0.0,
    @SerializedName("id")
    val id: String = "",
    @SerializedName("latitude")
    val latitude: Double = 0.0,
    @SerializedName("longitude")
    val longitude: Double = 0.0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("quality")
    val quality: Int = 0,
    @SerializedName("useCount")
    val useCount: Int = 0
)