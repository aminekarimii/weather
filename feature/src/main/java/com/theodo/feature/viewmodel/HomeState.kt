package com.theodo.feature.viewmodel

import com.theodo.domain.model.Weather

sealed class HomeState {
    object Idle: HomeState()
    object Loading : HomeState()
    object Refreshing : HomeState()
    data class Success(
        val weather: List<Weather?>
    ) : HomeState()
}