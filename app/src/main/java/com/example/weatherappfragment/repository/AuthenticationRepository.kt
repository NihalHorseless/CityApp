package com.example.weatherappfragment.repository

import android.app.Application
import com.google.firebase.auth.FirebaseAuth

class AuthenticationRepository() {

    private val auth = FirebaseAuth.getInstance()

    // Function to create a new user with username (which is actaully the email) and password
    fun createUser(username: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    // Function to sign in an existing user with username and password
    // We could not implement this feature fully but it is a good start for later improvements
    fun signIn(username: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }
    fun isUserSignedIn(): Boolean {
        return auth.currentUser != null
    }
}

