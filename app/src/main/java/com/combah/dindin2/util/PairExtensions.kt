package com.combah.dindin2.util

fun <T, R> Pair<T?, R?>.takeIfHasBoth(): Pair<T, R>? {
    val safeFirst = first
    val safeSecond = second
    return if (safeFirst != null && safeSecond != null) {
        Pair(safeFirst, safeSecond)
    } else {
        null
    }
}