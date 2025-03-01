package com.theodo.domain.model


data class Weather(
    val timelines: List<Timeline>
)

data class Timeline(
    val startTime: String,
    val endTime: String,
    val timeStep: String,
    val intervals: List<Interval>
)

data class Interval(
    val startTime: String,
    val values: Values
)

data class Values(
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
