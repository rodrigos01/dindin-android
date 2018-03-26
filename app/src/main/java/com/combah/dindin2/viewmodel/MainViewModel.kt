package com.combah.dindin2.viewmodel


import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.combah.dindin2.R
import com.combah.dindin2.repository.MovementRepository
import com.combah.dindin2.util.*
import com.combah.travel.util.asLiveList
import java.util.*

class MainViewModel(private val repository: MovementRepository) : ViewModel() {

    private val initialTime = MutableLiveData<Date>()
    private val endTime = MutableLiveData<Date>()
    private val dates = initialTime.with(endTime) { initial, end -> Pair(initial?.time, end?.time) }
            .map { it?.takeIfHasBoth() }
    private val totalValue = dates.then { it?.let { repository.totalInPeriod(it.first, it.second) } }

    val movements = dates.then { it?.let { repository.movementsInPeriod(it.first, it.second) } }
            .asLiveList()
    val total = totalValue.map { it?.asCurrency() }
    val income = dates.then { it?.let { repository.incomeInPeriod(it.first, it.second) } }
            .map { it?.asCurrency() }
    val expenses = dates.then { it?.let { repository.expenseInPeriod(it.first, it.second) } }
            .map { it?.asCurrency() }

    val monthString = initialTime.map { it?.let { makeMonthString(it) } }
    val valueColor = totalValue.map { it?.let { getValueColor(it) } }

    fun setPeriod(initial: Date, end: Date) {
        initialTime.value = initial
        endTime.value = end
    }

    private fun makeMonthString(initial: Date): String {
        val calendar = initial.asCalendar()
        val monthName = calendar
                .getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
        return String.format(Locale.getDefault(), "%s, %d", monthName, calendar.get(Calendar.YEAR))
    }

    private fun getValueColor(total: Double): Int {
        return when {
            total < 0 -> R.color.income
            total > 0 -> R.color.expense
            else -> R.color.colorPrimary
        }
    }
}