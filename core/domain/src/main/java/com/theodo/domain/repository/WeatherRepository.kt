package com.theodo.domain.repository

import com.theodo.domain.model.Timeline
import com.theodo.domain.model.Weather
import kotlinx.coroutines.flow.Flow


interface WeatherRepository {
    fun getWeather(): Flow<List<Weather?>>
}

