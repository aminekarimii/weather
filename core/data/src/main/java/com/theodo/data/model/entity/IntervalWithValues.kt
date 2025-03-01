package com.theodo.data.model.entity

import androidx.room.Embedded
import androidx.room.Relation


data class IntervalWithValues(
    @Embedded val interval: IntervalEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "intervalId"
    )
    val values: ValuesEntity
)
