package com.combah.dindin2.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.combah.dindin2.room.dao.MovementDao
import com.combah.dindin2.room.entities.RoomMovement

@Database(entities = [(RoomMovement::class)], version = 1)
@TypeConverters(Converters::class)
abstract class DinDinRoomDatabase : RoomDatabase() {
    abstract fun movementDao(): MovementDao
}