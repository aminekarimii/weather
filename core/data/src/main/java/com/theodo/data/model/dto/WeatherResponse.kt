package com.theodo.data.model.dto
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class WeatherResponse(
    @SerialName("data")
    val weatherData: WeatherData,
)

@Serializable
data class WeatherData(
    @SerialName("timelines")
    val timelineResponses: List<TimelineResponse>
)

@Serializable
data class TimelineResponse(
    val endTime: String,
    @SerialName("intervals")
    val intervalResponses: List<IntervalResponse>,
    val startTime: String,
    @SerialName("timestep")
    val timeStep: String
)

@Serializable
data class IntervalResponse(
    val startTime: String,
    val values: ValuesResponse
)

@Serializable
data class ValuesResponse(
    val cloudBase: Double?,
    val cloudCeiling: Double?,
    val cloudCover: Double?,
    val precipitationIntensity: Double?,
    val precipitationType: Int?,
    val temperature: Double?,
    val temperatureApparent: Double?,
    val weatherCode: Int?,
    val windDirection: Double?,
    val windGust: Double?,
    val windSpeed: Double?
)
