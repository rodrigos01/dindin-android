package com.combah.dindin2.util

fun Number.localizedString(): String {
    return java.text.NumberFormat.getInstance().format(this)
}

fun Number.asCurrency(): String {
    return java.text.NumberFormat.getCurrencyInstance().format(this)
}