package com.combah.dindin2.util

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

fun <T, R> LiveData<T>.map(block: (T?) -> R?): LiveData<R> {
    val newLiveData = MutableLiveData<R>()
    observeForever {
        newLiveData.postValue(block(it))
    }
    return newLiveData
}

fun <T, R, S> LiveData<T>.with(liveData: LiveData<R>, block: (T?, R?) -> S?): LiveData<S> {
    val newLiveData = MutableLiveData<S>()
    observeForever { first ->
        liveData.observeForever { second ->
            newLiveData.postValue(block(first, second))
        }
    }
    return newLiveData
}