package com.example.weather_app.data_layer.remote

import com.example.weather_app.data_layer.response.WeatherDataResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.request
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

class WeatherAPIClientImpl : RemoteDataSource{

    private val client: HttpClient = HttpClient(Android){
        install(Logging){
            logger = Logger.SIMPLE
        }
        install(ContentNegotiation){
            json(
                Json{
                    prettyPrint = true
                    isLenient = true
                }
            )
        }
    }

    companion object {
        private val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }
    override suspend fun getWeatherDataResponse(latitude: Float, longitude: Float): WeatherDataResponse {
        var counter: Int = 1
        lateinit var response: WeatherDataResponse
        runBlocking {
            repeat(counter){
                val job = async {
                    response = client.request("$BASE_URL/weather?lat=$latitude&lon=$longitude&appid=f3e685b13c89d08a79bebeaab3942472").body()
                }
                job.await()
                counter++
            }

        }

        return response
    }
}