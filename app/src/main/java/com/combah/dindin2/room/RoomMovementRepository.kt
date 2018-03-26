package com.combah.dindin2.room

import com.combah.dindin2.data.Movement
import com.combah.dindin2.repository.MovementRepository
import com.combah.dindin2.room.dao.MovementDao
import com.combah.dindin2.room.entities.RoomMovement
import com.combah.dindin2.util.map

class RoomMovementRepository(private val movementDao: MovementDao) : MovementRepository {

    override fun movementsInPeriod(start: Long, end: Long) =
            movementDao.movementsInPeriod(start, end).map { it?.toMovementList() }

    override fun incomeInPeriod(start: Long, end: Long) =
            movementDao.incomeInPeriod(start, end)

    override fun expenseInPeriod(start: Long, end: Long) =
            movementDao.expenseInPeriod(start, end)

    override fun totalInPeriod(start: Long, end: Long) =
            movementDao.totalInPeriod(start, end)

    private fun List<RoomMovement>.toMovementList() = map { roomMovement ->
        Movement(
                id = roomMovement.id,
                date = roomMovement.date,
                income = roomMovement.income,
                description = roomMovement.description,
                value = roomMovement.value
        )
    }
}