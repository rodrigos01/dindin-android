package com.combah.dindin2.room

import com.combah.dindin2.data.Movement
import com.combah.dindin2.repository.MovementRepository
import com.combah.dindin2.room.dao.MovementDao
import com.combah.dindin2.room.entities.RoomMovement
import com.combah.dindin2.util.map
import kotlinx.coroutines.experimental.async
import java.util.*

class RoomMovementRepository(private val movementDao: MovementDao) : MovementRepository {

    override fun movementsInPeriod(start: Date, end: Date) =
            movementDao.movementsInPeriod(start, end).map { it?.toMovementList() }

    override fun incomeInPeriod(start: Date, end: Date) =
            movementDao.incomeInPeriod(start, end)

    override fun expenseInPeriod(start: Date, end: Date) =
            movementDao.expenseInPeriod(start, end)

    override fun totalInPeriod(start: Date, end: Date) =
            movementDao.totalInPeriod(start, end)

    override fun saveMovement(movement: Movement) {
        async {
            movementDao.insert(RoomMovement(id = movement.id,
                    date = movement.date,
                    income = movement.income,
                    description = movement.description,
                    value = movement.value))
        }
    }

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