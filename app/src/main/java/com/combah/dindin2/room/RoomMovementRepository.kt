package com.combah.dindin2.room

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.combah.dindin2.data.Movement
import com.combah.dindin2.repository.MovementRepository
import com.combah.dindin2.room.dao.MovementDao
import com.combah.dindin2.room.entities.RoomMovement

class RoomMovementRepository(private val movementDao: MovementDao) : MovementRepository {

    override fun movementsInPeriod(start: Long, end: Long): LiveData<List<Movement>> {
        return movementDao.movementsInPeriod(start, end).asMovementListLiveData()
    }

    override fun incomeInPeriod(start: Long, end: Long): LiveData<Double> {
        return movementDao.incomeInPeriod(start, end)
    }

    override fun expenseInPeriod(start: Long, end: Long): LiveData<Double> {
        return movementDao.expenseInPeriod(start, end)
    }

    override fun totalInPeriod(start: Long, end: Long): LiveData<Double> {
        val totalLiveData = MutableLiveData<Double>()
        val income = incomeInPeriod(start, end)
        val expense = expenseInPeriod(start, end)
        val observer = Observer<Double> {
            totalLiveData.value = income.value?.minus(expense.value ?: 0.0)
        }
        income.observeForever(observer)
        expense.observeForever(observer)
        return totalLiveData
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