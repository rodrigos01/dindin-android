package com.combah.dindin2.data

import java.util.*

data class Movement(
        val id: String = UUID.randomUUID().toString(),
        val date: Date = Date(),
        val income: Boolean = false,
        val description: String? = null,
        val value: Double = 0.0
)