package com.example.weatherappfragment.ui.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherappfragment.repository.ChatCommentRepository
import com.example.weatherappfragment.ui.viewModel.CommentViewModel

class CommentViewModelFactory (private val repository: ChatCommentRepository, private val cityId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CommentViewModel(repository,cityId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}