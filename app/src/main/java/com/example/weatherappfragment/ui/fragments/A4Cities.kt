package com.example.weatherappfragment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappfragment.R
import com.example.weatherappfragment.data.sampleData
import com.example.weatherappfragment.model.City
import com.example.weatherappfragment.ui.adapter.CitiesAdapter
import com.example.weatherappfragment.util.MyNavigationListener


class A4Cities : Fragment(), MyNavigationListener<City> {

    private lateinit var cityList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_a4_cities, container, false)

        cityList = view.findViewById(R.id.cityList)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        val data = sampleData.citiesListData
        val adapter = CitiesAdapter(data, this)



        cityList.layoutManager = layoutManager
        cityList.adapter = adapter
    }

    override fun navigateToDetail(data: City) {
        val navController = findNavController()
        val action = A4CitiesDirections.actionA4CitiesToA5CityDetail(
            cityName = data.name,
            latitude = data.latitude.toFloat(),
            longitude = data.longitude.toFloat()
        )
        navController.navigate(action)
    }


}
