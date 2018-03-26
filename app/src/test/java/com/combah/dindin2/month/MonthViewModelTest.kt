package com.combah.dindin2.month

import com.combah.dindin2.repository.MovementRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MonthViewModelTest {

    private val movementRepository = mock<MovementRepository>()

    @Before
    fun setUp() {
    }

    @Test
    fun shouldGetMovements() {
        val viewModel = MonthViewModel(movementRepository)

        verify(movementRepository).movementsInPeriod(any(), any())
    }

    @Test
    fun shouldGetIncome() {
        val viewModel = MonthViewModel(movementRepository)

        verify(movementRepository).incomeInPeriod(any(), any())
    }

}