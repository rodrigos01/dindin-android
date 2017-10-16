package com.combah.dindin2.room

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Observer
import com.combah.dindin2.data.Movement
import com.combah.dindin2.repository.MovementRepository
import com.combah.dindin2.room.dao.MovementDao
import com.combah.dindin2.room.entities.RoomMovement

class RoomMovementRepository(private val movementDao: MovementDao) : MovementRepository {

    override fun movementsInPeriod(start: Long, end: Long): LiveData<List<Movement>> {
        return movementDao.movementsInPeriod(start, end).toMovementListLiveData()
    }

    override fun incomeInPeriod(start: Long, end: Long): LiveData<Double> {
        return movementDao.incomeInPeriod(start, end)
    }

    override fun expenseInPeriod(start: Long, end: Long): LiveData<Double> {
        return movementDao.expenseInPeriod(start, end)
    }

    override fun totalInPeriod(start: Long, end: Long): LiveData<Double> {
        val totalLiveData = MediatorLiveData<Double>()
        val income = incomeInPeriod(start, end)
        val expense = expenseInPeriod(start, end)
        totalLiveData.value = income.value?.minus(expense.value ?: 0.0)
        val observer = Observer<Double> {
            totalLiveData.value = income.value?.minus(expense.value ?: 0.0)
        }
        totalLiveData.addSource(income, observer)
        totalLiveData.addSource(expense, observer)
        return totalLiveData
    }

    private fun LiveData<List<RoomMovement>>.toMovementListLiveData(): LiveData<List<Movement>> {
        val result = MediatorLiveData<List<Movement>>()
        result.value = value?.toMovementList()
        result.addSource(this) {
            result.value = it?.toMovementList()
        }
        return result
    }

    private fun List<RoomMovement>.toMovementList(): List<Movement> {
        return map { roomMovement ->
            Movement(
                    id = roomMovement.id,
                    date = roomMovement.date,
                    income = roomMovement.income,
                    description = roomMovement.description,
                    value = roomMovement.value
            )
        }
    }
}