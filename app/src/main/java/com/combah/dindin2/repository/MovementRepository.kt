package com.combah.dindin2.repository

import android.arch.lifecycle.LiveData
import com.combah.dindin2.data.Movement

interface MovementRepository {
    fun movementsInPeriod(start: Long, end: Long): LiveData<List<Movement>>
    fun incomeInPeriod(start: Long, end: Long): LiveData<Double>
    fun expenseInPeriod(start: Long, end: Long): LiveData<Double>
    fun totalInPeriod(start: Long, end: Long): LiveData<Double>
}