package com.example.weatherappfragment.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherappfragment.repository.AuthenticationRepository

class UserLoginViewModel(private val repository: AuthenticationRepository) : ViewModel() {

    private val _isSignedIn = MutableLiveData<Boolean>()
    val isSignedIn: LiveData<Boolean> = _isSignedIn

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun createUser(username: String, password: String) {
        repository.createUser(username, password) { success, message ->
            _isSignedIn.value = success
            _errorMessage.value = message
        }
    }

    fun signIn(username: String, password: String) {
        repository.signIn(username, password) { success, message ->
            _isSignedIn.value = success
            _errorMessage.value = message
        }
    }

    fun checkUserLoggedIn() {
        _isSignedIn.value = repository.isUserSignedIn()
    }
}