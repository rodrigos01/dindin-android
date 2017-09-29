package com.combah.dindin2.room.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.combah.dindin2.room.entities.RoomMovement

@Dao
interface MovementDao {

    @Query("SELECT * from movements where date BETWEEN :start AND :end")
    fun movementsInPeriod(start: Long, end: Long): LiveData<List<RoomMovement>>
}