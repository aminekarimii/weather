package com.theodo.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import com.theodo.feature.HomeScreen
import com.theodo.weather.ui.theme.TheodoWeatherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            TheodoWeatherTheme {
                Scaffold { paddingValue ->
                    HomeScreen(paddingValue = paddingValue)
                }
            }
        }
    }
}