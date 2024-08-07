package com.example.weather_app.data_layer.remote

import com.example.weather_app.data_layer.response.WeatherDataResponse

interface RemoteDataSource {

    suspend fun getWeatherDataResponse(latitude: Float, longitude: Float) : WeatherDataResponse
}