package com.combah.dindin2.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.combah.dindin2.repository.MovementRepository
import com.combah.dindin2.room.DinDinRoomDatabase
import com.combah.dindin2.room.RoomMovementRepository
import com.combah.dindin2.room.dao.MovementDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DindinModule(private val context: Context) {

    @Provides
    fun providesApplicationContext() = context.applicationContext

    @Provides
    @Singleton
    fun providesMovementDao(context: Context): MovementDao {
        return Room.databaseBuilder(context, DinDinRoomDatabase::class.java, "dindin")
                .build()
                .movementDao()
    }

    @Provides
    fun providesMovementRepository(movementDao: MovementDao): MovementRepository = RoomMovementRepository(movementDao)
}