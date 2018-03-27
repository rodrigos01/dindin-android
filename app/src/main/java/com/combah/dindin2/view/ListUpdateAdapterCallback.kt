package com.combah.dindin2.view

import android.support.v7.util.ListUpdateCallback
import android.support.v7.widget.RecyclerView

class ListUpdateAdapterCallback(private val adapter: RecyclerView.Adapter<*>) : ListUpdateCallback {
    override fun onChanged(position: Int, count: Int, payload: Any?) {
        adapter.notifyItemRangeChanged(position, count)
    }

    override fun onMoved(fromPosition: Int, toPosition: Int) {
        adapter.notifyItemMoved(fromPosition, toPosition)
    }

    override fun onInserted(position: Int, count: Int) {
        adapter.notifyItemRangeInserted(position, count)
    }

    override fun onRemoved(position: Int, count: Int) {
        adapter.notifyItemRangeRemoved(position, count)
    }
}