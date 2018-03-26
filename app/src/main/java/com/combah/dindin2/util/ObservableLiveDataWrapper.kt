package com.combah.dindin2.util

import android.arch.lifecycle.LiveData
import android.databinding.ObservableField

class ObservableLiveDataWrapper<T>(liveData: LiveData<T>) : ObservableField<T>() {
    init {
        liveData.observeForever {
            set(it)
        }
    }
}