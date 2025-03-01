package com.theodo.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.theodo.data.model.entity.IntervalEntity
import com.theodo.data.model.entity.ValuesEntity
import com.theodo.data.model.entity.TimelineEntity
import com.theodo.data.model.entity.WeatherWithIntervals

@Dao
interface TheodoWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimeline(timeline: TimelineEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntervals(intervals: List<IntervalEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInterval(interval: IntervalEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertValues(values: List<ValuesEntity>): List<Long>

    @Transaction
    @Query("SELECT * FROM TimelineEntity ORDER BY id DESC")
    suspend fun getLatestWeather(): List<WeatherWithIntervals?>
}
