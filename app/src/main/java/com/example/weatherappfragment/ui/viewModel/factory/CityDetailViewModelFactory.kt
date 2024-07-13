package com.example.weatherappfragment.ui.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherappfragment.repository.ApiRepository
import com.example.weatherappfragment.repository.CityRepository
import com.example.weatherappfragment.ui.viewModel.CityDetailViewModel

class CityDetailViewModelFactory(private val repository: CityRepository,private val apiRepository: ApiRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CityDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CityDetailViewModel(cityRepository = repository, apiRepository = apiRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}