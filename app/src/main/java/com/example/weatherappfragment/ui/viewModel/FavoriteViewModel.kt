package com.example.weatherappfragment.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappfragment.model.roomEntity.Favorites
import com.example.weatherappfragment.repository.CityRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val cityRepository: CityRepository) : ViewModel() {
    private val _favCities = MutableLiveData<List<Favorites>>()
    val favCities: LiveData<List<Favorites>>
        get() = _favCities

    fun getFavCities() {
        viewModelScope.launch {
            val response = cityRepository.getCities()
            _favCities.postValue(response)
        }
    }

}
