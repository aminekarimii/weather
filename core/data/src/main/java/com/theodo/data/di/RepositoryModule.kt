package com.theodo.data.di

import com.theodo.data.repository.WeatherRepositoryImpl
import com.theodo.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun weatherRepository(weatherRepository: WeatherRepositoryImpl): WeatherRepository
}