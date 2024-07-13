package com.example.weatherappfragment.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherappfragment.model.ChatMessage
import com.example.weatherappfragment.model.Comment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatCommentRepository(private val database: FirebaseDatabase) {

    fun getChatMessages(cityId: String): LiveData<List<ChatMessage>> {
        val chatRef = database.getReference("cities/$cityId/chats")
        val messagesLiveData = MutableLiveData<List<ChatMessage>>()

        val listener = chatRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val messages = mutableListOf<ChatMessage>()
                for (child in dataSnapshot.children) {
                    val message = child.getValue(ChatMessage::class.java)
                    if (message != null) {
                        messages.add(message)
                    }
                }
                messagesLiveData.value = messages
                Log.d("ChatCommentRepository", messagesLiveData.value.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
            }
        })

        return messagesLiveData
    }

    fun getComments(cityId: String): LiveData<List<Comment>> {
        val commentRef = database.getReference("cities/$cityId/comments")
        val commentsLiveData = MutableLiveData<List<Comment>>()


        val listener = commentRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val comments = mutableListOf<Comment>()
                for (child in dataSnapshot.children) {
                    val comment = child.getValue(Comment::class.java)
                    if (comment != null) {
                        comments.add(comment)
                    }
                }
                commentsLiveData.value = comments
                Log.d("ChatCommentRepository", commentsLiveData.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
            }
        })

        return commentsLiveData
    }

    fun postChatMessage(cityId: String, message: ChatMessage) {
        val chatRef = database.getReference("cities/$cityId/chats").push()
        val messageMap = mapOf(
            "content" to message.content,
            "senderID" to message.senderID,
            "timestamp" to message.timestamp
        )
        chatRef.setValue(messageMap)
        Log.d("ChatCommentRepository", messageMap.toString())
        Log.d("ChatCommentRepository", chatRef.toString())
    }

    fun postComment(cityId: String, comment: Comment) {
        val commentRef = database.getReference("cities/$cityId/comments").push()
        comment.commentId = commentRef.key ?: "Unknown Key"
        val commentMap = mapOf(
            "content" to comment.content,
            "senderID" to comment.senderID,
            "timestamp" to comment.timestamp,
            "commentId" to comment.commentId
        )
        commentRef.setValue(commentMap)
        Log.d("ChatCommentRepository", commentMap.toString())
        Log.d("ChatCommentRepository", commentRef.toString())
    }
    fun deleteComment(cityId: String, comment: Comment) {
        val commentRef = database.getReference("cities/$cityId/comments")
        val commentId = comment.commentId

        if (commentId != null) {
            commentRef.child(commentId).removeValue()
                .addOnSuccessListener { Log.d("ChatCommentRepository", "Comment deleted successfully") }
                .addOnFailureListener { e -> Log.e("ChatCommentRepository", "Error deleting comment", e) }
        } else {
            Log.e("ChatCommentRepository", "Comment key is null, cannot delete comment")
        }
    }
}
