package com.example.weather_app

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weather_app.data_layer.model.WeatherInfo
import com.example.weather_app.ui.theme.Weather_appTheme
import com.example.weather_app.ui.theme.daySky
import com.example.weather_app.ui.theme.nightSky
import com.example.weather_app.view_models.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Weather_appTheme {
                MainFrame()
            }
        }
    }
}


@Composable
fun MainFrame(modifier: Modifier = Modifier)  {
    WeatherInfoLayout()
}

@SuppressLint("DiscouragedApi")
@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun WeatherInfoLayoutPreview() {
    val context = LocalContext.current
    val weather = WeatherInfo(
        "Praia Grande",
        "weather_${R.drawable.weather_01n}",
        "Sunny",
        18,
        "Friday",
        false
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = nightSky
    ) {
        Column(
            modifier = Modifier
                .background(nightSky)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = weather.locationName)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = weather.dayOfWeek)

            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(
                    id = R.drawable.weather_01n
                ),
                contentDescription = null
            )
        }
    }


}


@SuppressLint("DiscouragedApi")
@Composable
fun WeatherInfoLayout(context: Context = LocalContext.current, modifier: Modifier = Modifier) {
    val viewModel = viewModel(
        modelClass = MainViewModel::class,
        factory = object : ViewModelProvider.Factory{
            override fun <T:ViewModel> create(modelClass: Class<T>):T{
                return MainViewModel() as T
            }
        })
    val weatherInfoState by viewModel.weatherInfo.collectAsState()

    val backGroundColor: Color = when (weatherInfoState?.isDay) {
        true -> daySky
        else -> {
            nightSky
        }
    }

    //identifier is not working anymore
    val iconDrawableResId: Int = when(weatherInfoState?.conditionIcon){
        "clear sky" -> if (weatherInfoState?.isDay == true){ R.drawable.weather_01d } else { R.drawable.weather_01n }
        "few clouds" -> if(weatherInfoState?.isDay == true) { R.drawable.weather_02d} else { R.drawable.weather_02n }
        "scattered clouds" -> if(weatherInfoState?.isDay == true) { R.drawable.weather_03d } else { R.drawable.weather_03n }
        "broken clouds" -> if(weatherInfoState?.isDay == true) { R.drawable.weather_04d} else { R.drawable.weather_04n }
        "shower rain" -> if(weatherInfoState?.isDay == true) { R.drawable.weather_09d} else { R.drawable.weather_09n }
        "rain" -> if(weatherInfoState?.isDay == true) { R.drawable.weather_10d } else { R.drawable.weather_10n }
        "thunderstorm" -> if(weatherInfoState?.isDay == true) { R.drawable.weather_11d } else { R.drawable.weather_11n }
        "snow" -> if(weatherInfoState?.isDay == true) { R.drawable.weather_13d } else { R.drawable.weather_13n }
        "mist" -> if(weatherInfoState?.isDay == true) { R.drawable.weather_50d } else { R.drawable.weather_50n }
        else -> { R.drawable.ic_launcher_foreground}
    }



    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backGroundColor
    ) {
        Column(
            modifier = Modifier
                .background(backGroundColor)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = weatherInfoState?.locationName.toString(),
                fontSize = 48.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${weatherInfoState?.dayOfWeek.toString()}",
                fontSize = 28.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp).padding(16.dp))

            Image(
                painter = painterResource(id = iconDrawableResId),
                contentDescription = null
            )

            Text(
                text = "${weatherInfoState?.temperature}º",
                fontSize = 48.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }

}


