package com.combah.travel.util

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.v7.util.DiffUtil
import android.support.v7.util.ListUpdateCallback

abstract class LiveList<E>(private val diffCallback: DiffCallback<E>? = null) : LiveData<List<E>>(), List<E> {

    private val listUpdateCallbacks = ArrayList<ListUpdateCallback>()
    private var diffResult: DiffUtil.DiffResult? = null

    private val callBackObserverMap = HashMap<ListUpdateCallback, Observer<List<E>>>()

    private var backingList: List<E>? = null

    fun observe(owner: LifecycleOwner, callback: ListUpdateCallback) {
        val observer = Observer<List<E>> {
            diffResult?.dispatchUpdatesTo(callback)
        }
        callBackObserverMap[callback] = observer
        super.observe(owner, observer)
    }

    fun removeObserver(callback: ListUpdateCallback) {
        callBackObserverMap.remove(callback)?.let {
            super.removeObserver(it)
        }
    }

    override fun setValue(value: List<E>?) {
        diffResult = DiffUtil.calculateDiff(InnerDiffCallback(value), true)
        backingList = value?.map { it }
        super.setValue(value)
    }

    override val size: Int
        get() = backingList?.size ?: 0

    interface DiffCallback<in E> {
        fun areItemsTheSame(oldItem: E, newItem: E): Boolean
        fun areContentsTheSame(oldItem: E, newItem: E): Boolean
    }

    private inner class InnerDiffCallback(val newList: List<E>?) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            if (backingList == null && newList == null) {
                return true
            }
            val oldItem = backingList?.get(oldItemPosition)
            val newItem = newList?.get(newItemPosition)

            if (oldItem == null && newItem == null) {
                return true
            } else if (oldItem == null || newItem == null) {
                return false
            }

            return diffCallback?.areItemsTheSame(oldItem, newItem) ?: (oldItem == newItem)
        }

        override fun getOldListSize(): Int {
            return backingList?.size ?: 0
        }

        override fun getNewListSize(): Int {
            return newList?.size ?: 0
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            if (diffCallback == null) {
                return areItemsTheSame(oldItemPosition, newItemPosition)
            } else {
                val oldItem = backingList?.get(oldItemPosition)
                val newItem = newList?.get(newItemPosition)

                if (oldItem == null && newItem == null) {
                    return true
                } else if (oldItem == null || newItem == null) {
                    return false
                }

                return diffCallback.areContentsTheSame(oldItem, newItem)
            }
        }
    }
}