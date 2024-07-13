package com.example.weatherappfragment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatherappfragment.R
import com.example.weatherappfragment.ui.viewModel.SplashViewModel

class A1Splash : Fragment() {

    private lateinit var viewModel: SplashViewModel

    private lateinit var horizontalProgressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity())[SplashViewModel::class.java]
        return inflater.inflate(R.layout.fragment_a1_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        horizontalProgressBar = view.findViewById(R.id.loadingBar)
        observeNavigation()
    }

    private fun observeNavigation() {
        viewModel.navigateToMainScreen.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                // Navigate to main screen
                findNavController().navigate(R.id.action_a1Splash_to_a2Main)
            }
        }
    }
}