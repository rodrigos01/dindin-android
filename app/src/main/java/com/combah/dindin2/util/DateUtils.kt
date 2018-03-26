package com.combah.dindin2.util

import java.util.*

fun getInitialDateOfMonth(calendar: Calendar): Date {
    val tempCalendar = Calendar.getInstance()
    tempCalendar.time = calendar.time
    tempCalendar.set(Calendar.DAY_OF_MONTH,
            calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
    tempCalendar.setTimeToBeginningOfDay()
    return tempCalendar.time
}

fun getEndDateOfMonth(calendar: Calendar): Date {
    val tempCalendar = Calendar.getInstance()
    tempCalendar.time = calendar.time
    tempCalendar.set(Calendar.DAY_OF_MONTH,
            calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
    tempCalendar.setTimeToEndOfDay()
    return tempCalendar.time
}