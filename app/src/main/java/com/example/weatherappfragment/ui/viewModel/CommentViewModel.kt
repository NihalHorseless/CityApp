package com.example.weatherappfragment.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.weatherappfragment.model.Comment
import com.example.weatherappfragment.repository.ChatCommentRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CommentViewModel(private val repository: ChatCommentRepository, private val cityId: String) : ViewModel() {

    val comments: LiveData<List<Comment>> = repository.getComments(cityId)

    fun postComment(content: String) {
        val comment = Comment(
            senderID = FirebaseAuth.getInstance().currentUser!!.email?:"Johnwayne07@gmail.com",
            content = content,
            timestamp = System.currentTimeMillis()
        )
        Log.d("CommentViewModel",  comment.toString())
        repository.postComment(cityId, comment)
    }
    fun deleteComment(comment: Comment) {
        repository.deleteComment(cityId, comment)
    }
}
