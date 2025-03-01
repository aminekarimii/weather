package com.theodo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.theodo.data.model.entity.IntervalEntity
import com.theodo.data.model.entity.ValuesEntity
import com.theodo.data.model.entity.TimelineEntity


const val DATABASE_VERSION = 1

@Database(
    entities = [
        TimelineEntity::class,
        IntervalEntity::class,
        ValuesEntity::class
    ],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class TheodoDatabase : RoomDatabase() {
    abstract fun weatherDao(): TheodoWeatherDao
}