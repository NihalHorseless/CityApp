package com.example.weatherappfragment.ui.viewModel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherappfragment.repository.ApiRepository
import com.example.weatherappfragment.ui.viewModel.MainViewModel

class MainViewModelFactory(private val context: Context,private val apiRepository: ApiRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(context,apiRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}