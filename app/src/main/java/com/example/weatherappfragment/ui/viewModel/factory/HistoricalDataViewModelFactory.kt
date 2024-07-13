package com.example.weatherappfragment.ui.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherappfragment.repository.CityRepository
import com.example.weatherappfragment.ui.viewModel.HistoricalDataViewModel

class HistoricalDataViewModelFactory(private val repository: CityRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoricalDataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoricalDataViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}