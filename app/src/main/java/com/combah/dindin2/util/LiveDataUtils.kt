package com.combah.dindin2.util

import android.arch.lifecycle.MutableLiveData

fun <T> MutableLiveData(initialValue: T) = MutableLiveData<T>().apply { value = initialValue }