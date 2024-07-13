package com.example.weatherappfragment.ui.viewModel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappfragment.model.response.WeatherResponse
import com.example.weatherappfragment.repository.ApiRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val context: Context,private val apiRepository: ApiRepository) : ViewModel() {

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    private val _lastLocation = MutableLiveData<Location>()
    val lastLocation: LiveData<Location> = _lastLocation

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse>
        get() = _weatherData

    fun fetchData(longtitude: Float, latitude: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiRepository.fetchData(
                    longtitude = longtitude,
                    latitude = latitude
                )
                Log.d("TAG", "Fetched data: $response")
                _weatherData.postValue(response)

            } catch (e: Exception) {
                Log.d("TAG", "Error fetching data: $e")
            }
        }
    }

    fun getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("MainViewModel", "Location Access Denied")

        } else {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                _lastLocation.value = location
                Log.d("MainViewModel", "Location: ${location.latitude}")
            }
        }
    }

    fun requestLocationUpdates(locationRequest: LocationRequest, callback: LocationCallback) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("MainViewModel", "Location Update Denied")
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, callback, null)
    }

    fun removeLocationUpdates(callback: LocationCallback) {
        fusedLocationClient.removeLocationUpdates(callback)
    }
}