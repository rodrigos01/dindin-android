package com.combah.dindin2.util

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData

class ObservableLiveDataWrapper<T>(liveData: LiveData<T>) : ObservableField<T>() {
    init {
        liveData.observeForever {
            set(it)
        }
    }
}