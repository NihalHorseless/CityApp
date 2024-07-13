package com.example.weatherappfragment.model.response


import com.google.gson.annotations.SerializedName

data class Stations(
    @SerializedName("F1417")
    val f1417: F1417,
    @SerializedName("F2280")
    val f2280: F1417,
    @SerializedName("KJRB")
    val kJRB: KJRB,
    @SerializedName("KLGA")
    val kLGA: KLGA,
    @SerializedName("KNYC")
    val kNYC: KNYC,
    @SerializedName("NJ12")
    val nJ12: NJ12
)