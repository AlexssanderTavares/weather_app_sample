package com.example.weather_app.data_layer.repositories

import com.example.weather_app.data_layer.model.WeatherInfo
import com.example.weather_app.data_layer.remote.RemoteDataSource
import com.example.weather_app.data_layer.remote.WeatherAPIClientImpl
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.roundToInt

class WeatherRepositoryImpl() : WeatherRepository {

    private lateinit var remoteDataSource: RemoteDataSource

    override suspend fun getWeatherData(latitude: Float, longitude: Float): WeatherInfo {
        remoteDataSource = WeatherAPIClientImpl()
        val response = this.remoteDataSource.getWeatherDataResponse(latitude,longitude)
        val weather = response.weather[0]
        val info = WeatherInfo(
            locationName = response.name,
            conditionIcon = weather.description,
            condition = weather.main,
            temperature = (response.main.temp - 273).roundToInt(),
            dayOfWeek = LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()),
            isDay = weather.icon.last() == 'd'
        )
        return info

    }
}