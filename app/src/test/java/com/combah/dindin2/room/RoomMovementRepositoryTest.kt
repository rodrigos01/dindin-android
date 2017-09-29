package com.combah.dindin2.room

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
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
        whenever(roomMovementLiveData.value).thenReturn(roomMovementList)
        whenever(roomMovementLiveData.observeForever(any<Observer<List<RoomMovement>?>>())).then {
            (it.arguments[0] as Observer<List<RoomMovement>?>).onChanged(roomMovementList)
        }
    }

    @Test
    fun movementsShouldHaveSameValues() {
        val roomMovements = movementDao.movementsInPeriod(Long.MIN_VALUE, Long.MAX_VALUE)
        val repository = RoomMovementRepository(movementDao)
        val result = repository.movementsInPeriod(Long.MIN_VALUE, Long.MAX_VALUE)
        assertEquals(roomMovements.value?.size, result.value?.size)
        assertNotNull(roomMovements.value?.find { roomMovement ->
            val equal = result.value?.find { movement ->
                movement.id == roomMovement.id
                        && movement.date == roomMovement.date
                        && movement.income == roomMovement.income
                        && movement.description == roomMovement.description
                        && movement.value == roomMovement.value
            }
            equal != null
        })
    }

}