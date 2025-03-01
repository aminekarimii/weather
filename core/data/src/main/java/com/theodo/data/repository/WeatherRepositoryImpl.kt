package com.theodo.data.repository

import com.theodo.data.database.TheodoWeatherDao
import com.theodo.data.mapper.toDomain
import com.theodo.data.mapper.toEntity
import com.theodo.data.model.entity.IntervalEntity
import com.theodo.data.model.entity.TimelineEntity
import com.theodo.data.model.entity.ValuesEntity
import com.theodo.data.network.TheodoApiService
import com.theodo.domain.model.Weather
import com.theodo.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: TheodoApiService,
    private val dao: TheodoWeatherDao
) : WeatherRepository {

    override fun getWeather(): Flow<List<Weather?>> {
        return flow {
            try {
                val response = weatherApi.getWeather()
                response.body()?.weatherData?.let { weatherData ->
                    val weatherEntity = weatherData.timelineResponses.map { timeline ->
                        timeline.toEntity()
                    }
                    weatherEntity.map {
                        saveWeatherData(
                            it.timelineEntities,
                            it.intervals,
                            it.values
                        )
                    }
                }
            } catch (e: Exception) {
                // todo
            } finally {
                emit(dao.getLatestWeather().map {
                    it?.toDomain()
                })
            }
        }
    }

    private suspend fun saveWeatherData(
        timelineEntity: TimelineEntity,
        intervals: List<IntervalEntity>,
        values: List<ValuesEntity>
    ) {
        val weatherId = dao.insertTimeline(timelineEntity)

        val intervalIds = intervals.map { interval ->
            interval.copy(weatherId = weatherId.toInt())
        }.map { updatedInterval ->
            dao.insertInterval(updatedInterval)
        }

        val updatedValues = values.mapIndexed { index, value ->
            value.copy(intervalId = intervalIds[index].toInt())
        }

        dao.insertValues(updatedValues)
    }
}