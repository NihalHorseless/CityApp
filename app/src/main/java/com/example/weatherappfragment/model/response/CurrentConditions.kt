package com.example.weatherappfragment.model.response


import com.google.gson.annotations.SerializedName

data class CurrentConditions(
    @SerializedName("cloudcover")
    val cloudcover: Double = 0.0, // Percent cloud cover
    @SerializedName("conditions")
    val conditions: String = "",
    @SerializedName("datetime")
    val datetime: String = "", // Date and time in ISO 8601 format with offset
    @SerializedName("datetimeEpoch")
    val datetimeEpoch: Int = 0, // Unix timestamp in seconds
    @SerializedName("dew")
    val dew: Double = 0.0, // Dew point temperature in degrees Celsius
    @SerializedName("feelslike")
    val feelslike: Double = 0.0, // Feels like temperature in degrees Celsius
    @SerializedName("humidity")
    val humidity: Double = 0.0, // Humidity percentage
    @SerializedName("icon")
    val icon: String = "", // Weather condition icon code
    @SerializedName("moonphase")
    val moonphase: Double = 0.0, // Moon phase (0-1)
    @SerializedName("precip")
    val precip: Double = 0.0, // Precipitation amount in millimeters
    @SerializedName("precipprob")
    val precipprob: Double = 0.0, // Probability of precipitation (0-1)
    @SerializedName("preciptype")
    val preciptype: Any? = null, // Can be null or a string representing precipitation type (e.g. "rain")
    @SerializedName("pressure")
    val pressure: Double = 0.0, // Atmospheric pressure in hPa
    @SerializedName("snow")
    val snow: Double = 0.0, // Snowfall amount in millimeters
    @SerializedName("snowdepth")
    val snowdepth: Double = 0.0, // Snow depth on ground in centimeters
    @SerializedName("solarenergy")
    val solarenergy: Double = 0.0, // Amount of solar energy available in W/m²
    @SerializedName("solarradiation")
    val solarradiation: Double = 0.0, // Solar radiation level in W/m²
    @SerializedName("source")
    val source: String = "", // Data source
    @SerializedName("stations")
    val stations: List<String> = emptyList(), // List of weather station IDs
    @SerializedName("sunrise")
    val sunrise: String = "", // Sunrise time in ISO 8601 format with offset
    @SerializedName("sunriseEpoch")
    val sunriseEpoch: Int = 0, // Sunrise Unix timestamp in seconds
    @SerializedName("sunset")
    val sunset: String = "", // Sunset time in ISO 8601 format with offset
    @SerializedName("sunsetEpoch")
    val sunsetEpoch: Int = 0, // Sunset Unix timestamp in seconds
    @SerializedName("temp")
    val temp: Double = 0.0, // Current temperature in degrees Celsius
    @SerializedName("uvindex")
    val uvindex: Double = 0.0, // UV index
    @SerializedName("visibility")
    val visibility: Double = 0.0, // Visibility distance in kilometers
    @SerializedName("winddir")
    val winddir: Double = 0.0, // Wind direction in degrees (0-360)
    @SerializedName("windgust")
    val windgust: Double = 0.0, // Wind gust speed in meters per second
    @SerializedName("windspeed")
    val windspeed: Double = 0.0 // Wind speed in meters per second
)