package com.combah.dindin2.util

import java.util.*

fun Date.getInitialDateOfMonth(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(Calendar.DAY_OF_MONTH,
            calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
    calendar.setTimeToBeginningOfDay()
    return calendar.time
}

fun Date.getEndDateOfMonth(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(Calendar.DAY_OF_MONTH,
            calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
    calendar.setTimeToEndOfDay()
    return calendar.time
}

fun Date.asCalendar(): Calendar {
    return Calendar.getInstance().also { it.time = this }
}

fun Calendar.setTimeToBeginningOfDay() {
    set(java.util.Calendar.HOUR_OF_DAY, 0)
    set(java.util.Calendar.MINUTE, 0)
    set(java.util.Calendar.SECOND, 0)
    set(java.util.Calendar.MILLISECOND, 0)
}

fun Calendar.setTimeToEndOfDay() {
    set(java.util.Calendar.HOUR_OF_DAY, 23)
    set(java.util.Calendar.MINUTE, 59)
    set(java.util.Calendar.SECOND, 59)
    set(java.util.Calendar.MILLISECOND, 999)
}

fun Calendar.move(field: Int, offset: Int) {
    set(field, get(field) + offset)
}