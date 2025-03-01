package com.theodo.feature.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theodo.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val _state = MutableStateFlow<HomeState>(HomeState.Idle)
    val state: StateFlow<HomeState> = _state

    init {
        getWeather()
    }

    fun getWeather(isRefresh: Boolean = false) {
        viewModelScope.launch {
            _state.value = if (isRefresh) HomeState.Refreshing else HomeState.Loading

            weatherRepository.getWeather()
                .map { weather ->
                    _state.value = weather?.let {
                        Log.d("GlobalTag", it.toString())
                        HomeState.Success(it)
                    } ?: HomeState.Idle
                }.collect()
        }
    }
}