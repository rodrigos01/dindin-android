package com.combah.dindin2.util

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData(initialValue: T) = MutableLiveData<T>().apply { value = initialValue }