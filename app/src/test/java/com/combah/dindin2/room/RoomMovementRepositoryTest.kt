package com.combah.dindin2.room

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.combah.dindin2.data.Movement
import com.combah.dindin2.room.dao.MovementDao
import com.combah.dindin2.room.entities.RoomMovement
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.*
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class RoomMovementRepositoryTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val roomMovementList = listOf(
            RoomMovement(id = "A", date = Date(), income = false, description = "mvmt A", value = 6.5),
            RoomMovement(id = "B", date = Date(), income = true, description = "mvmt B", value = 9.35),
            RoomMovement(id = "C", date = Date(), income = false, description = "mvmt C", value = 5.49)
    )
    private val roomMovementLiveData = mock<LiveData<List<RoomMovement>>>()
    private val movementDao = mock<MovementDao>()

    @Before
    fun setUp() {
        whenever(movementDao.movementsInPeriod(any(), any())).thenReturn(roomMovementLiveData)
        whenever(roomMovementLiveData.observeForever(any<Observer<List<RoomMovement>?>>())).then {
            (it.arguments[0] as Observer<List<RoomMovement>?>).onChanged(roomMovementList)
        }

        whenever(movementDao.incomeInPeriod(any(), any()))
                .thenReturn(MutableLiveData<Double>().apply { value = 9.35 })
        whenever(movementDao.expenseInPeriod(any(), any()))
                .thenReturn(MutableLiveData<Double>().apply { value = 11.99 })
        whenever(movementDao.totalInPeriod(any(), any()))
                .thenReturn(MutableLiveData<Double>().apply { value = -2.64 })
    }

    @Test
    fun movementsShouldHaveSameValues() {
        val repository = RoomMovementRepository(movementDao)

        val queue = ArrayBlockingQueue<List<Movement>>(1)
        repository.movementsInPeriod(Long.MIN_VALUE, Long.MAX_VALUE).observeForever { queue.add(it) }
        val result = queue.poll(10, TimeUnit.SECONDS)
        assertEquals(roomMovementList.size, result?.size)
        assertNotNull(roomMovementList.find { roomMovement ->
            val equal = result?.find { movement ->
                movement.id == roomMovement.id
                        && movement.date == roomMovement.date
                        && movement.income == roomMovement.income
                        && movement.description == roomMovement.description
                        && movement.value == roomMovement.value
            }
            equal != null
        })
    }

    @Test
    fun incomeShouldBeDaoIncome() {
        val repository = RoomMovementRepository(movementDao)
        val income = repository.incomeInPeriod(Long.MIN_VALUE, Long.MAX_VALUE)
        assertEquals(9.35, income.value)
    }

    @Test
    fun expenseShouldBeDaoExpense() {
        val repository = RoomMovementRepository(movementDao)
        val expense = repository.expenseInPeriod(Long.MIN_VALUE, Long.MAX_VALUE)
        assertEquals(11.99, expense.value)
    }

    @Test
    fun totalShouldBeDaoTotal() {
        val repository = RoomMovementRepository(movementDao)
        val total = repository.totalInPeriod(Long.MIN_VALUE, Long.MAX_VALUE)
        assertEquals(-2.64, total.value)
    }

}