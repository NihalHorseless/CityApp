package com.example.weatherappfragment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappfragment.R
import com.example.weatherappfragment.data.database.CityDatabase
import com.example.weatherappfragment.repository.CityRepository
import com.example.weatherappfragment.ui.adapter.HistoricalDataAdapter
import com.example.weatherappfragment.ui.viewModel.HistoricalDataViewModel
import com.example.weatherappfragment.ui.viewModel.factory.HistoricalDataViewModelFactory


class A6HistoricalData : Fragment() {

    private lateinit var averageTemp: TextView
    private lateinit var sunnyDays: TextView

    private lateinit var logList: RecyclerView

    private lateinit var historicalDataViewModel: HistoricalDataViewModel

    val args: A6HistoricalDataArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_a6_historical_data, container, false)

        logList = view.findViewById(R.id.historical_Data_list)
        averageTemp = view.findViewById(R.id.historical_data_avgTempTxt)
        sunnyDays = view.findViewById(R.id.historical_data_sunDaysTxt)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        val adapter = HistoricalDataAdapter(emptyList())
        logList.layoutManager = layoutManager
        logList.adapter = adapter

        val cityDb = CityDatabase.getInstance(requireContext())
        val cityRepository = CityRepository(cityDb)

        val viewModelFactory = HistoricalDataViewModelFactory(cityRepository)
        historicalDataViewModel =
            ViewModelProvider(this, viewModelFactory)[HistoricalDataViewModel::class.java]
        historicalDataViewModel.getLogs(args.cityName)
        historicalDataViewModel.logData.observe(viewLifecycleOwner) { logs ->
            adapter.setData(logs)
            historicalDataViewModel.getAverageTemp(logs)
            historicalDataViewModel.getCommonCondition(logs)
        }
        historicalDataViewModel.averageTemp.observe(viewLifecycleOwner) {
            averageTemp.text = it.toInt().toString() + "°F"
        }
        historicalDataViewModel.commonCondition.observe(viewLifecycleOwner) {
            sunnyDays.text = it
        }
    }

}