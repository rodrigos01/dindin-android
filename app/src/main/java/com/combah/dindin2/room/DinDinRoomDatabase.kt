package com.combah.dindin2.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.combah.dindin2.room.dao.MovementDao
import com.combah.dindin2.room.entities.RoomMovement

@Database(entities = arrayOf(RoomMovement::class), version = 1)
@TypeConverters(Converters::class)
abstract class DinDinRoomDatabase : RoomDatabase() {
    abstract fun movementDao(): MovementDao
}