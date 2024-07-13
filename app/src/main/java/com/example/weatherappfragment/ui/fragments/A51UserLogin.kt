package com.example.weatherappfragment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.weatherappfragment.R
import com.example.weatherappfragment.repository.AuthenticationRepository
import com.example.weatherappfragment.ui.viewModel.UserLoginViewModel
import com.example.weatherappfragment.ui.viewModel.factory.UserLoginViewModelFactory


class A51UserLogin : Fragment() {
    private lateinit var viewModel: UserLoginViewModel

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    private lateinit var signUpButton: Button
    private lateinit var loginBtn: Button

    private lateinit var loginTextView: TextView

    private val args: A51UserLoginArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_a5_1_user_login, container, false)

        emailEditText = view.findViewById(R.id.user_login_emailInput)
        passwordEditText = view.findViewById(R.id.user_login_passwordInput)
        signUpButton = view.findViewById(R.id.user_login_registerButton)
        loginBtn = view.findViewById(R.id.user_login_loginBtn)
        loginTextView = view.findViewById(R.id.user_login_labelTxt)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val authenticationRepo = AuthenticationRepository()
        val viewModelFactory = UserLoginViewModelFactory(authenticationRepo)
        viewModel = ViewModelProvider(this, viewModelFactory)[UserLoginViewModel::class.java]

        // Checks whether user is already logged in or not but it is not fully implemented yet
        viewModel.checkUserLoggedIn()
        if (viewModel.isSignedIn.value == true) {
            //  navigateToOtherScreen()
        }
        // Observes errorMessage LiveData to display error messages
        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            if (!message.isNullOrEmpty()) {
                // Show error message using Toast
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }
        loginBtn.setOnClickListener {
            val email = emailEditText.text.toString().trim() ?: ""
            val password = passwordEditText.text.toString().trim() ?: ""

            try {
                viewModel.signIn(email, password)
                viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
                    if (message.isNullOrEmpty()) {
                        navigateToOtherScreen()
                    } else {
                        // Login fails, so it displays error message
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: IllegalArgumentException) {
                Toast.makeText(
                    activity,
                    "Please enter a valid email and password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Handles sign up button click
        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            try {
                viewModel.createUser(email, password)
                if (viewModel.isSignedIn.value == true) {
                    navigateToOtherScreen()
                } else {
                    Toast.makeText(
                        activity,
                        viewModel.errorMessage.value.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: IllegalArgumentException) {
                Toast.makeText(
                    activity,
                    "Please enter a valid email and password",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }


    }

    private fun navigateToOtherScreen() {
        val navController = findNavController()
        when (args.cityMenu) {
            "Chat" -> {
                val action =
                    A51UserLoginDirections.actionA51UserLoginToA52CityChatScreen(args.cityName)
                navController.navigate(action)
            }

            "Comment" -> {
                val action =
                    A51UserLoginDirections.actionA51UserLoginToA53CityComment(args.cityName)
                navController.navigate(action)
            }
        }

    }
}