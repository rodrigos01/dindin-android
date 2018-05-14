package com.combah.dindin2.room.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.combah.dindin2.room.entities.RoomMovement
import java.util.*

@Dao
interface MovementDao {

    @Query("SELECT * from movements where date BETWEEN :start AND :end")
    fun movementsInPeriod(start: Date, end: Date): LiveData<List<RoomMovement>>

    @Query("SELECT SUM(value) from movements where date BETWEEN :start AND :end and income = 1")
    fun incomeInPeriod(start: Date, end: Date): LiveData<Double>

    @Query("SELECT SUM(value) from movements where date BETWEEN :start AND :end and income = 0")
    fun expenseInPeriod(start: Date, end: Date): LiveData<Double>

    @Query("SELECT SUM(i.value) - SUM(e.value) from movements i, movements e where i.date BETWEEN :start AND :end AND i.income = 1 AND e.date BETWEEN :start AND :end AND e.income = 0")
    fun totalInPeriod(start: Date, end: Date): LiveData<Double>

    @Insert
    fun insert(movement: RoomMovement)
}