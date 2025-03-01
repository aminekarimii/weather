package com.theodo.data.mapper

import com.theodo.data.model.dto.TimelineResponse
import com.theodo.data.model.entity.IntervalEntity
import com.theodo.data.model.entity.TimelineEntity
import com.theodo.data.model.entity.ValuesEntity
import com.theodo.data.model.entity.WeatherWithIntervals
import com.theodo.domain.model.Interval
import com.theodo.domain.model.Timeline
import com.theodo.domain.model.Weather
import com.theodo.domain.model.Values

/* Map From Entity  to Domain */
fun WeatherWithIntervals.toDomain(): Weather {
    return Weather(
        timelines = this.intervals.map { interval ->
            Timeline(
                startTime = interval.interval.startTime,
                endTime = this.weather.endTime,
                timeStep = this.weather.timeStep,
                intervals = this.intervals.map {
                    Interval(
                        startTime = it.interval.startTime,
                        values = it.values.toDomain()
                    )
                }
            )
        }
    )
}

fun ValuesEntity.toDomain() = Values(
    cloudBase, cloudCeiling, cloudCover, precipitationIntensity, precipitationType,
    temperature, temperatureApparent, weatherCode, windDirection, windGust, windSpeed
)


/* Map From DTO to Entity */
fun TimelineResponse.toEntity(): WeatherWithEntities {
    val timelineEntity =
        TimelineEntity(
            startTime = this.startTime,
            endTime = this.endTime,
            timeStep = this.timeStep
        )


    val intervals = this.intervalResponses.map {
        IntervalEntity(
            weatherId = 0,
            startTime = it.startTime
        )
    }

    val values = this.intervalResponses.map {
        ValuesEntity(
            intervalId = 0,
            cloudBase = it.values.cloudBase,
            cloudCeiling = it.values.cloudCeiling,
            cloudCover = it.values.cloudCover,
            precipitationIntensity = it.values.precipitationIntensity,
            precipitationType = it.values.precipitationType,
            temperature = it.values.temperature,
            temperatureApparent = it.values.temperatureApparent,
            weatherCode = it.values.weatherCode,
            windDirection = it.values.windDirection,
            windGust = it.values.windGust,
            windSpeed = it.values.windSpeed
        )
    }

    return WeatherWithEntities(
        timelineEntity,
        intervals,
        values
    )
}

data class WeatherWithEntities(
    val timelineEntities: TimelineEntity,
    val intervals: List<IntervalEntity>,
    val values: List<ValuesEntity>
)
