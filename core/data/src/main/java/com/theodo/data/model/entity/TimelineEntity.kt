package com.theodo.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TimelineEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val startTime: String,
    val endTime: String,
    val timeStep: String
)

@Entity
data class IntervalEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val weatherId: Int,
    val startTime: String,
)

@Entity
data class ValuesEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val intervalId: Int,
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
