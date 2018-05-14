package com.combah.dindin2.repository

import androidx.lifecycle.LiveData
import com.combah.dindin2.data.Movement
import java.util.*

interface MovementRepository {
    fun movementsInPeriod(start: Date, end: Date): LiveData<List<Movement>>
    fun incomeInPeriod(start: Date, end: Date): LiveData<Double>
    fun expenseInPeriod(start: Date, end: Date): LiveData<Double>
    fun totalInPeriod(start: Date, end: Date): LiveData<Double>
    fun saveMovement(movement: Movement)
}