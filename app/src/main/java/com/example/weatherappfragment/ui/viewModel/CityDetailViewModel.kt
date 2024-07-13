package com.example.weatherappfragment.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappfragment.model.response.CurrentConditions
import com.example.weatherappfragment.model.roomEntity.Favorites
import com.example.weatherappfragment.model.roomEntity.Forecast
import com.example.weatherappfragment.repository.ApiRepository
import com.example.weatherappfragment.repository.CityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class CityDetailViewModel(
    private val cityRepository: CityRepository,
    private val apiRepository: ApiRepository
) : ViewModel() {
    private val _city = MutableLiveData<Favorites>()
    val city: LiveData<Favorites>
        get() = _city

    private val _isCityLiked = MutableLiveData<Boolean>()
    val isCityLiked: LiveData<Boolean>
        get() = _isCityLiked

    private val _weatherData = MutableLiveData<CurrentConditions>()
    val weatherData: LiveData<CurrentConditions>
        get() = _weatherData
    private val _logExists = MutableLiveData<Boolean>()
    val logExists: LiveData<Boolean>
        get() = _logExists

    fun fetchData(longtitude: Float, latitude: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiRepository.fetchData(
                    longtitude = longtitude,
                    latitude = latitude
                )
                Log.d("TAG", "Fetched data: $response")
                _weatherData.postValue(response.currentConditions)

            } catch (e: Exception) {
                Log.d("TAG", "Error fetching data: $e")
            }
        }
    }

    fun addToFav(city: Favorites) {
        viewModelScope.launch {
            city.let {
                cityRepository.insert(it)
            }

        }
    }

    fun addForecast(forecast: Forecast) {
        viewModelScope.launch {
            forecast.let {
                cityRepository.insertForecast(forecast)
            }
        }
    }

    fun checkFav(cityName: String) {
        viewModelScope.launch {
            val isLiked = cityRepository.isCityLiked(cityName)
            _isCityLiked.postValue(isLiked)
        }
    }

    fun checkForecast(cityName: String, date: String) {
        viewModelScope.launch {
            val isForecastExists =
                cityRepository.forecastExists(cityName = cityName, logDate = date)
            _logExists.postValue(isForecastExists)
        }
    }

    fun deleteFav(city: Favorites) {
        viewModelScope.launch {
            cityRepository.delete(city)
        }
    }

    fun deleteByCityName(cityName: String) {
        viewModelScope.launch {
            cityRepository.deleteByCityName(cityName)
        }
    }

}