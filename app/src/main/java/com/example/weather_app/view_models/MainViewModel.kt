package com.example.weather_app.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_app.data_layer.model.WeatherInfo
import com.example.weather_app.data_layer.repositories.WeatherRepository
import com.example.weather_app.data_layer.repositories.WeatherRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {


    private lateinit var repository: WeatherRepository

    private val _weatherInfo: MutableStateFlow<WeatherInfo?> = MutableStateFlow(null)
    val weatherInfo = _weatherInfo.asStateFlow()

    fun showWeatherInfo(latitude: Float, longitude: Float){
        repository = WeatherRepositoryImpl()
        viewModelScope.launch(Dispatchers.Main) {
            delay(3000L)
            _weatherInfo.value = repository.getWeatherData(latitude,longitude)
        }
    }

    init {
        showWeatherInfo( -25.4284F, -49.2733F)
    }
}