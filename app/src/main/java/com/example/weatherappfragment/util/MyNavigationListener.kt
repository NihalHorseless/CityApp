package com.example.weatherappfragment.util

import com.example.weatherappfragment.model.City


interface MyNavigationListener<T> {
    fun navigateToDetail(data: T)
}