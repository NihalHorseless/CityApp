package com.example.weatherappfragment.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(): ViewModel() {

    private val _navigateToMainScreen = MutableLiveData<Boolean>(false)
    val navigateToMainScreen: LiveData<Boolean> = _navigateToMainScreen

    init {
        // Simulate some delay (replace with actual data loading logic)
        viewModelScope.launch {
            delay(10000) // Delay for 3 seconds
            _navigateToMainScreen.postValue(true)
        }
    }

}