package com.combah.dindin2.room.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "movements")
data class RoomMovement(
        @PrimaryKey
        val id: String,
        val date: Date,
        val income: Boolean,
        val description: String?,
        val value: Double
)