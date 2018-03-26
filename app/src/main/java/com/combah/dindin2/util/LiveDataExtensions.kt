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

fun <T> LiveData<T>.filter(block: (T?) -> Boolean): LiveData<T> {
    val newLiveData = MutableLiveData<T>()
    observeForever {
        if (block(it)) {
            newLiveData.postValue(it)
        }
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

fun <T, R> LiveData<T>.then(block: (T?) -> LiveData<R>?): LiveData<R> {
    val newLiveData = MutableLiveData<R>()
    observeForever { first ->
        block(first)?.observeForever { second ->
            newLiveData.postValue(second)
        }
    }
    return newLiveData
}