package com.example.weather_app.data_layer.repositories

import com.example.weather_app.data_layer.model.WeatherInfo

interface WeatherRepository {

    suspend fun getWeatherData(latitude: Float, longitude: Float) : WeatherInfo
}