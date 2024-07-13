package com.example.weatherappfragment.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappfragment.model.roomEntity.Favorites
import com.example.weatherappfragment.model.roomEntity.Forecast
import com.example.weatherappfragment.repository.CityRepository
import kotlinx.coroutines.launch

class HistoricalDataViewModel(private val cityRepository: CityRepository): ViewModel() {
    private val _logData = MutableLiveData<List<Forecast>>()
    val logData: LiveData<List<Forecast>>
        get() = _logData

    private val _averageTemp = MutableLiveData<Double>()
    val averageTemp: LiveData<Double>
        get() = _averageTemp

    private val _commonCondition = MutableLiveData<String>()
    val commonCondition: LiveData<String>
        get() = _commonCondition

    fun getLogs(cityName: String) {
        viewModelScope.launch {
            val response = cityRepository.getForecast(cityName)
            _logData.postValue(response)
        }
    }
    fun getAverageTemp(logData: List<Forecast>) {
        var sum = 0.0
        for (forecast in logData) {
            sum += forecast.temp
        }
        _averageTemp.value = sum / logData.size

    }
    fun getCommonCondition(logData: List<Forecast>) {
        // Check if there's any data
        if (logData.isEmpty()) {
            return
        }

        val conditionMap = mutableMapOf<String, Int>()
        logData.forEach { forecast ->
            val condition = forecast.condition
            val count = conditionMap[condition] ?: 0
            conditionMap[condition] = count + 1
        }

        // Find the condition with the highest count
        val mostCommonCondition = conditionMap.maxByOrNull { entry -> entry.value }?.key

        _commonCondition.value = mostCommonCondition ?: "-------------"
    }
}