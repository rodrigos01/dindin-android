package com.combah.dindin2.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.databinding.ObservableInt
import com.combah.dindin2.R


import com.combah.dindin2.data.Movement
import com.combah.dindin2.repository.MovementRepository
import com.combah.dindin2.util.asCalendar
import com.combah.dindin2.util.asCurrency
import com.combah.travel.util.LiveList
import com.combah.travel.util.LiveMutableList
import java.util.*

class MainViewModel(private val repository: MovementRepository) : ViewModel() {

    private val _movements = LiveMutableList<Movement>()
    val movements: LiveList<Movement>
        get() = _movements

    val monthString = ObservableField<String>()

    val income = ObservableField<String>()
    val expenses = ObservableField<String>()
    val total = ObservableField<String>()

    val valueColor = ObservableInt()

    fun setPeriod(initial: Date, end: Date) {
        repository.movementsInPeriod(initial.time, end.time).observeForever {
            _movements.value = it
        }

        updateMonthString(initial)

        repository.incomeInPeriod(initial.time, end.time).observeForever(income)
        repository.expenseInPeriod(initial.time, end.time).observeForever(expenses)

        val total = repository.totalInPeriod(initial.time, end.time)
        total.observeForever(this.total)
        total.observeForever {
            updateValueColor(it ?: 0.0)
        }
    }

    private fun updateMonthString(initial: Date) {
        val calendar = initial.asCalendar()
        val monthName = calendar
                .getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
        val monthStringValue = String.format(Locale.getDefault(), "%s, %d", monthName, calendar.get(Calendar.YEAR))
        monthString.set(monthStringValue)
    }

    private fun updateValueColor(total: Double) {
        val color = when {
            total < 0 -> R.color.income
            total > 0 -> R.color.expense
            else -> R.color.colorPrimary
        }
        valueColor.set(color)
    }

    private fun LiveData<Double>.observeForever(field: ObservableField<String>) {
        this.observeForever {
            val value = it ?: 0.0
            field.set(value.asCurrency())
        }
    }
}