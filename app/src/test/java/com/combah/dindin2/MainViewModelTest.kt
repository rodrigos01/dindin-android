package com.combah.dindin2

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.combah.dindin2.data.Movement
import com.combah.dindin2.repository.MovementRepository
import com.combah.dindin2.util.getEndDateOfMonth
import com.combah.dindin2.util.getInitialDateOfMonth
import com.combah.dindin2.viewmodel.MainViewModel
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val repository = mock<MovementRepository>()
    private val movementListLiveData = mock<LiveData<List<Movement>>>()
    private val movementList = listOf(
            Movement(id = "A", date = Date(), income = false, description = "mvmt A", value = 6.5),
            Movement(id = "B", date = Date(), income = true, description = "mvmt B", value = 9.35),
            Movement(id = "C", date = Date(), income = false, description = "mvmt C", value = 5.49)
    )

    @Before
    fun setup() {
        whenever(repository.movementsInPeriod(any(), any())).thenReturn(movementListLiveData)
        whenever(movementListLiveData.observeForever(any())).then {
            (it.arguments[0] as Observer<List<Movement>?>).onChanged(movementList)
        }

        whenever(repository.incomeInPeriod(any(), any()))
                .thenReturn(MutableLiveData<Double>().apply { value = 9.35 })
        whenever(repository.expenseInPeriod(any(), any()))
                .thenReturn(MutableLiveData<Double>().apply { value = 11.99 })
        whenever(repository.totalInPeriod(any(), any()))
                .thenReturn(MutableLiveData<Double>().apply { value = movementList.sumByDouble { it.value } })

    }

    @Test
    fun shouldGetMovementsInPeriodWhenPeriodSet() {
        val viewModel = MainViewModel(repository)
        val date = Date()
        val initial = date.getInitialDateOfMonth()
        val end = date.getEndDateOfMonth()
        viewModel.setPeriod(initial, end)

        verify(repository).movementsInPeriod(initial.time, end.time)
    }

    @Test
    fun shouldUpdateMovementsWhenPeriodSet() {
        val viewModel = MainViewModel(repository)
        setPeriod(viewModel)
        Assert.assertEquals(movementList, viewModel.movements.value)
    }

    private fun setPeriod(viewModel: MainViewModel) {
        val date = Date()
        val initial = date.getInitialDateOfMonth()
        val end = date.getEndDateOfMonth()
        viewModel.setPeriod(initial, end)
    }
}