package com.theodo.data.model.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class WeatherWithIntervals(
    @Embedded val weather: TimelineEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "weatherId",
        entity = IntervalEntity::class
    )
    val intervals: List<IntervalWithValues>
)