package com.combah.dindin2.month

import android.arch.lifecycle.ViewModel
import com.combah.dindin2.repository.MovementRepository
import com.combah.dindin2.util.ObservableLiveDataWrapper
import com.combah.dindin2.util.getEndDateOfMonth
import com.combah.dindin2.util.getInitialDateOfMonth
import java.util.*

class MonthViewModel(movementRepository: MovementRepository) : ViewModel() {

    private val calendar = Calendar.getInstance()

    val movements = movementRepository.movementsInPeriod(getInitialDateOfMonth(calendar).time,
            getEndDateOfMonth(calendar).time)

    val income = ObservableLiveDataWrapper(movementRepository.incomeInPeriod(getInitialDateOfMonth(calendar).time,
            getEndDateOfMonth(calendar).time))
}