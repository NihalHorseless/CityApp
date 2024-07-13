package com.example.weatherappfragment.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.weatherappfragment.R
import com.example.weatherappfragment.data.database.CityDatabase
import com.example.weatherappfragment.model.response.CurrentConditions
import com.example.weatherappfragment.model.roomEntity.Favorites
import com.example.weatherappfragment.model.roomEntity.Forecast
import com.example.weatherappfragment.network.RetrofitHelper
import com.example.weatherappfragment.repository.ApiRepository
import com.example.weatherappfragment.repository.CityRepository
import com.example.weatherappfragment.ui.viewModel.CityDetailViewModel
import com.example.weatherappfragment.ui.viewModel.factory.CityDetailViewModelFactory
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date


class A5CityDetail : Fragment() {

    private lateinit var cityName: TextView
    private lateinit var cityTimeZone: TextView
    private lateinit var cityWeather: TextView
    private lateinit var cityTemp: TextView

    private lateinit var saveBtn: ImageButton
    private lateinit var favBtn: ImageButton
    private lateinit var historyBtn: ImageButton
    private lateinit var chatBtn: ImageButton
    private lateinit var commentBtn: ImageButton

    private lateinit var weatherIcon: ImageView


    val args: A5CityDetailArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_a5_city_detail, container, false)

        cityName = view.findViewById(R.id.city_detail_name)
        cityTimeZone = view.findViewById(R.id.city_detail_timezone)
        cityWeather = view.findViewById(R.id.city_detail_weather_info)
        cityTemp = view.findViewById(R.id.city_Detail_temp)

        historyBtn = view.findViewById(R.id.city_Detail_historyBtn)
        saveBtn = view.findViewById(R.id.city_Detail_saveBtn)
        favBtn = view.findViewById(R.id.city_detail_likeBtn)
        chatBtn = view.findViewById(R.id.city_detail_chat_Btn)
        commentBtn = view.findViewById(R.id.city_detail_comments_Btn)

        weatherIcon = view.findViewById(R.id.city_detail_weather_img)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cityDb = CityDatabase.getInstance(requireContext())
        val cityRepository = CityRepository(cityDb)


        val viewModelFactory = CityDetailViewModelFactory(
            cityRepository, ApiRepository(
                RetrofitHelper.getMyApi()
            )
        )
        val cityDetailViewModel =
            ViewModelProvider(this, viewModelFactory)[CityDetailViewModel::class.java]
        cityDetailViewModel.checkFav(args.cityName)

        cityName.text = args.cityName

        cityDetailViewModel.fetchData(longtitude = args.longitude, latitude = args.latitude)
        initializeFields(cityDetailViewModel.weatherData.value ?: CurrentConditions())

        cityDetailViewModel.weatherData.observe(viewLifecycleOwner, Observer {
            initializeFields(it)
        })

        cityDetailViewModel.isCityLiked.observe(viewLifecycleOwner, Observer { liked ->
            cityDetailViewModel.checkFav(args.cityName)
        })

        val currentLocalDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy") // Set the desired format
        val date = currentLocalDate.format(formatter)

        cityDetailViewModel.checkForecast(cityName = args.cityName, date = date)

        cityDetailViewModel.logExists.observe(viewLifecycleOwner, Observer { exists ->
            cityDetailViewModel.checkForecast(cityName = args.cityName, date = date)
        })
        chatBtn.setOnClickListener {
            val navController = findNavController()
            val action = A5CityDetailDirections.actionA5CityDetailToA51UserLogin(
                cityName = args.cityName, cityMenu = "Chat"
            )
            navController.navigate(action)
        }
        commentBtn.setOnClickListener {
            val navController = findNavController()
            val action = A5CityDetailDirections.actionA5CityDetailToA51UserLogin(
                cityName = args.cityName, cityMenu = "Comment"
            )
            navController.navigate(action)
        }
        saveBtn.setOnClickListener {
            if (cityDetailViewModel.logExists.value == false) {
                cityDetailViewModel.addForecast(
                    Forecast(
                        dateTime = date.toString(),
                        cityName = args.cityName,
                        temp = cityDetailViewModel.weatherData.value?.temp
                            ?: 0.0,
                        condition = cityDetailViewModel.weatherData.value?.conditions ?: "Unknown"
                    )
                )
            }
            else
                Toast.makeText(activity, "Already Saved", Toast.LENGTH_SHORT).show()
        }


        favBtn.setOnClickListener {
            if (cityDetailViewModel.isCityLiked.value == false) {
                cityDetailViewModel.addToFav(
                    Favorites(
                        cityName = args.cityName,
                        cityLatitude = args.latitude,
                        cityLongitude = args.longitude,
                        id = 0
                    )
                )
                Toast.makeText(activity, "Added to the Favorites!", Toast.LENGTH_SHORT).show()
                cityDetailViewModel.checkFav(args.cityName)
            } else {
                cityDetailViewModel.deleteByCityName(args.cityName)
                Toast.makeText(activity, "Removed From Favorites!", Toast.LENGTH_SHORT).show()
                cityDetailViewModel.checkFav(args.cityName)
            }
            cityDetailViewModel.checkFav(args.cityName)
            Log.d("Inside Final", cityDetailViewModel.isCityLiked.value.toString())
        }
        historyBtn.setOnClickListener {
            val navController = findNavController()
            val action = A5CityDetailDirections.actionA5CityDetailToA6HistoricalData(
                cityName = args.cityName
            )
            navController.navigate(action)
        }

    }

    fun initializeFields(response: CurrentConditions) {
        cityTimeZone.text = response.datetime
        cityWeather.text = response.conditions
        cityTemp.text = response.temp.toString() + "°F"
        when (response.conditions) {
            "Sunny", "Clear" -> weatherIcon.setImageResource(R.drawable.baseline_wb_sunny_24)
            "Partially cloudy", "Overcast", "Cloudy", "Rain, Partially cloudy","Rain, Overcast" -> weatherIcon.setImageResource(
                R.drawable.baseline_wb_cloudy
            )

            else -> weatherIcon.setImageResource(R.drawable.baseline_autorenew_24)

        }

    }

}