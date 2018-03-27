package com.combah.dindin2.viewmodel


import android.arch.lifecycle.ViewModel
import com.combah.dindin2.repository.MovementRepository
import com.combah.dindin2.util.*
import com.combah.travel.util.asLiveList
import java.util.*
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: MovementRepository) : ViewModel() {

    private val initialTime = MutableLiveData(Calendar.getInstance().time.getInitialDateOfMonth())
    private val endTime = MutableLiveData(Calendar.getInstance().time.getEndDateOfMonth())
    private val dates = initialTime.with(endTime) { initial, end -> Pair(initial?.time, end?.time) }
            .map { it?.takeIfHasBoth() }
    val movements = dates.then { it?.let { repository.movementsInPeriod(it.first, it.second) } }
            .asLiveList()

    val income = dates.then { it?.let { repository.incomeInPeriod(it.first, it.second) } }
            .map { it?.asCurrency() }
    val expenses = dates.then { it?.let { repository.expenseInPeriod(it.first, it.second) } }
            .map { it?.asCurrency() }
    val total = dates.then { it?.let { repository.totalInPeriod(it.first, it.second) } }

    val monthString = initialTime.map { it?.let { makeMonthString(it) } }

    fun setPeriod(initial: Date, end: Date) {
        initialTime.value = initial
        endTime.value = end
    }

    fun previousPeriod() = movePeriod(-1)
    fun nextPeriod() = movePeriod(1)

    private fun movePeriod(offset: Int) {
        dates.observeOnce {
            val initial = it?.first?.let { Date(it) }?.asCalendar()?.apply { move(Calendar.MONTH, offset) }?.time
            val end = it?.second?.let { Date(it) }?.asCalendar()?.apply { move(Calendar.MONTH, offset) }?.time
            if (initial != null && end != null) {
                setPeriod(initial, end)
            }
        }
    }

    private fun makeMonthString(initial: Date): String {
        val calendar = initial.asCalendar()
        val monthName = calendar
                .getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
        return String.format(Locale.getDefault(), "%s, %d", monthName, calendar.get(Calendar.YEAR))
    }
}