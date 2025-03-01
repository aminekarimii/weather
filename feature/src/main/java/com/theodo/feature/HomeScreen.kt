package com.theodo.feature

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.theodo.domain.model.Weather
import com.theodo.feature.viewmodel.HomeState
import com.theodo.feature.viewmodel.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    paddingValue: PaddingValues
) {
    val pullToRefreshState = remember {
        object : PullToRefreshState {
            private val anim = Animatable(0f, Float.VectorConverter)
            override val distanceFraction
                get() = anim.value
            override val isAnimating: Boolean
                get() = true

            override suspend fun animateToThreshold() {
                anim.animateTo(1f, spring(dampingRatio = Spring.DampingRatioHighBouncy))
            }

            override suspend fun animateToHidden() {
                anim.animateTo(0f)
            }

            override suspend fun snapTo(targetValue: Float) {
                anim.snapTo(targetValue)
            }
        }
    }

    val state = viewModel.state.collectAsState().value

    Surface(modifier = Modifier.fillMaxSize()) {
        PullToRefreshBox(
            state = pullToRefreshState,
            isRefreshing = state is HomeState.Refreshing,
            onRefresh = {
                viewModel.getWeather(true)
            },
        ) {
            when (state) {
                is HomeState.Loading -> {
                    Loading()
                }

                is HomeState.Success -> {
                    WeatherContent(
                        weatherData = state.weather,
                        paddingValue = paddingValue,
                    )
                }

                else -> Unit
            }
        }
    }
}

@Composable
fun Loading() {
    Text(
        text = "Loading...",
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun WeatherContent(
    paddingValue: PaddingValues,
    weatherData: List<Weather?>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValue)
    ) {
        Row {
            Text(
                text = "Today's Weather",
                style = MaterialTheme.typography.titleLarge
            )

            // TODO: to be moved to the ViewModel
            val currentWeather =
                weatherData.filterNotNull().flatMap { it.timelines }
                    .firstOrNull { it.timeStep == "current" }?.intervals?.first()?.values

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = currentWeather?.windGust.toString(),
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = currentWeather?.temperature.toString(),
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = currentWeather?.temperatureApparent.toString(),
                    style = MaterialTheme.typography.titleLarge
                )

            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(weatherData) { timeline ->
                    timeline?.timelines?.forEach {
                        val intervalValue = it.intervals.firstOrNull()?.values
                        Column(modifier = Modifier.padding(vertical = 8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Temperature:",
                                    style = MaterialTheme.typography.bodyLarge
                                )

                                Text(
                                    text = intervalValue?.temperature.toString(),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Apparent Temperature:",
                                    style = MaterialTheme.typography.bodyLarge
                                )

                                Text(
                                    text = intervalValue?.temperatureApparent.toString(),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Wind Speed:",
                                    style = MaterialTheme.typography.bodyLarge
                                )

                                Text(
                                    text = intervalValue?.windSpeed.toString(),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }
        }
    }


}