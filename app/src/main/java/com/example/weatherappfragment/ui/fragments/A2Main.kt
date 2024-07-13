package com.example.weatherappfragment.ui.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.weatherappfragment.R
import com.example.weatherappfragment.model.response.WeatherResponse
import com.example.weatherappfragment.network.RetrofitHelper
import com.example.weatherappfragment.repository.ApiRepository
import com.example.weatherappfragment.ui.viewModel.MainViewModel
import com.example.weatherappfragment.ui.viewModel.factory.MainViewModelFactory
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult

class A2Main : Fragment() {


    private lateinit var cityButton: Button
    private lateinit var favoriteButton: Button

    private lateinit var location: TextView
    private lateinit var temperature: TextView

    private lateinit var weatherIcon: ImageView

    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_a2_main, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityButton = view.findViewById(R.id.btnCities)
        favoriteButton = view.findViewById(R.id.btnFavCities)
        location = view.findViewById(R.id.txtLocation)
        temperature = view.findViewById(R.id.txtTemp)
        weatherIcon = view.findViewById(R.id.imgWeather)

        val viewModelFactory = this.context?.let {
            MainViewModelFactory(
                it, ApiRepository(
                    RetrofitHelper.getMyApi()
                )
            )
        }
        mainViewModel = ViewModelProvider(this, viewModelFactory!!)[MainViewModel::class.java]


        mainViewModel.lastLocation.observe(viewLifecycleOwner, { location ->
            if (location != null) {
                Log.d("MainFragment", "Location: $location.latitude, ${location.longitude}")
            }
        })
        mainViewModel.fetchData(
            longtitude = mainViewModel.weatherData.value?.longitude?.toFloat() ?: 0.0f,
            latitude = mainViewModel.weatherData.value?.latitude?.toFloat() ?: 0.0f
        )
        Log.d("Main Fragment", "Weather Data: ${mainViewModel.weatherData.value}")
        initializeFields(mainViewModel.weatherData.value ?: WeatherResponse())
        mainViewModel.weatherData.observe(viewLifecycleOwner, Observer {
            initializeFields(it)
        })


        // Call this when you need last known location
        mainViewModel.getLastKnownLocation()

        // Configure location request (set interval, accuracy, etc.)
        val locationRequest = LocationRequest.create()
        // ...

        // Request location updates when needed
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation
                // Update UI with new location data
                // ...
            }
        }
        mainViewModel.requestLocationUpdates(locationRequest, locationCallback)

        // Remember to remove location updates when fragment is not in foreground
        getViewLifecycleOwner().lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.DESTROYED) {
                mainViewModel.removeLocationUpdates(locationCallback)
            }
        }

        cityButton.setOnClickListener {
            findNavController().navigate(R.id.action_a2Main_to_a4Cities)
        }
        favoriteButton.setOnClickListener {
            findNavController().navigate(R.id.action_a2Main_to_a3FavoriteCities)
        }

    }

    fun initializeFields(response: WeatherResponse) {
        location.text = response.currentConditions.conditions
        temperature.text = response.currentConditions.temp.toString() + "°F"
        when (response.currentConditions.conditions) {
            "Sunny", "Clear" -> weatherIcon.setImageResource(R.drawable.baseline_wb_sunny_24)
            "Partially cloudy", "Overcast", "Cloudy", "Rain, Partially cloudy" -> weatherIcon.setImageResource(
                R.drawable.baseline_wb_cloudy
            )

            else -> weatherIcon.setImageResource(R.drawable.baseline_autorenew_24)

        }

    }

    companion object {
        const val TAG = "Main Fragment"
        const val LOCATION_PERMISSION_CODE = 101
    }

}