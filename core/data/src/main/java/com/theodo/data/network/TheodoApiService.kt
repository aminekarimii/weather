package com.theodo.data.network

import com.theodo.data.model.dto.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET

interface TheodoApiService {

    @GET("/weather")
    suspend fun getWeather(): Response<WeatherResponse>

}
