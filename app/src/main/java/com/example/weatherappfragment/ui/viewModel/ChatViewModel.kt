package com.example.weatherappfragment.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.weatherappfragment.model.ChatMessage
import com.example.weatherappfragment.repository.ChatCommentRepository
import com.google.firebase.auth.FirebaseAuth

class ChatViewModel(private val repository: ChatCommentRepository, private val cityId: String) : ViewModel() {

    val messages: LiveData<List<ChatMessage>> = repository.getChatMessages(cityId)


    fun postChatMessage(content: String) {
        val chatMessage = ChatMessage(
            FirebaseAuth.getInstance().currentUser!!.email?: "Johnny Baby",
            content,
            System.currentTimeMillis()
        )
        repository.postChatMessage(cityId, chatMessage)
    }
}
