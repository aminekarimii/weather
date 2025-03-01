package com.theodo.data.di

import android.content.Context
import androidx.room.Room
import com.theodo.data.database.TheodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideTheodoDatabase(
        @ApplicationContext
        context: Context
    ): TheodoDatabase {
        return Room.databaseBuilder(
            context,
            TheodoDatabase::class.java,
            "theodo_database.db"
        ).build()
    }

    @Provides
    fun provideNimbleWeatherDao(database: TheodoDatabase) = database.weatherDao()


}