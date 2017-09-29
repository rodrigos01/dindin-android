package com.combah.dindin2.room

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.combah.dindin2.data.Movement
import com.combah.dindin2.repository.MovementRepository
import com.combah.dindin2.room.dao.MovementDao
import com.combah.dindin2.room.entities.RoomMovement

class RoomMovementRepository(private val movementDao: MovementDao) : MovementRepository {

    override fun movementsInPeriod(start: Long, end: Long): LiveData<List<Movement>> {
        return movementDao.movementsInPeriod(start, end).asMovementListLiveData()
    }

    private fun LiveData<List<RoomMovement>>.asMovementListLiveData(): LiveData<List<Movement>> {
        val result = MutableLiveData<List<Movement>>()
        observeForever {
            result.value = it?.map { roomMovement ->
                Movement(
                        id = roomMovement.id,
                        date = roomMovement.date,
                        income = roomMovement.income,
                        description = roomMovement.description,
                        value = roomMovement.value
                )
            }
        }
        return result
    }
}