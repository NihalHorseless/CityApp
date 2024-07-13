package com.example.weatherappfragment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappfragment.R
import com.example.weatherappfragment.data.database.CityDatabase
import com.example.weatherappfragment.model.roomEntity.Favorites
import com.example.weatherappfragment.repository.CityRepository
import com.example.weatherappfragment.ui.adapter.FavoriteCitiesAdapter
import com.example.weatherappfragment.ui.viewModel.FavoriteViewModel
import com.example.weatherappfragment.ui.viewModel.factory.FavoriteViewModelFactory
import com.example.weatherappfragment.util.MyNavigationListener


class A3FavoriteCities : Fragment(), MyNavigationListener<Favorites> {
    companion object {
        const val TAG = "A3FavoriteCities"
    }

    private lateinit var filterBtn: Button
    private lateinit var citySearch: SearchView
    private lateinit var cityList: RecyclerView

    private lateinit var favoriteViewModel: FavoriteViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_a3_favorite_cities, container, false)

        cityList = view.findViewById(R.id.favorite_cities_recyclerView)
        citySearch = view.findViewById(R.id.favorite_cities_searchView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val layoutManager = LinearLayoutManager(context)
        val adapter = FavoriteCitiesAdapter(emptyList(), this)

        cityList.layoutManager = layoutManager
        cityList.adapter = adapter

        val cityDb = CityDatabase.getInstance(requireContext())
        val cityRepository = CityRepository(cityDb)



        val viewModelFactory = FavoriteViewModelFactory(cityRepository)
        favoriteViewModel = ViewModelProvider(this, viewModelFactory)[FavoriteViewModel::class.java]
        favoriteViewModel.getFavCities()
        favoriteViewModel.favCities.observe(viewLifecycleOwner, Observer { favorites ->
            adapter.setData(favorites)
        })
        citySearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                favoriteViewModel.favCities.value?.let { favorites ->
                    val filteredList = favorites.filter { favorite ->
                        favorite.cityName.lowercase().contains(newText.lowercase())
                    }
                    (cityList.adapter as FavoriteCitiesAdapter).setData(filteredList)
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
        })

    }

    override fun navigateToDetail(data: Favorites) {
        val navController = findNavController()
        val action = A3FavoriteCitiesDirections.actionA3FavoriteCitiesToA5CityDetail(
            cityName = data.cityName,
            latitude = data.cityLatitude,
            longitude = data.cityLongitude
        )
        navController.navigate(action)
    }


}