package com.example.weatherappfragment.ui.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherappfragment.repository.AuthenticationRepository
import com.example.weatherappfragment.ui.viewModel.UserLoginViewModel

class UserLoginViewModelFactory(private val repository: AuthenticationRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserLoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserLoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}