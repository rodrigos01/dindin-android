package com.combah.dindin2.util

import android.databinding.BindingAdapter
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView

@BindingAdapter("android:textColor")
fun setTextColor(view: TextView, resId: Int) {
    val color = ContextCompat.getColor(view.context, resId)
    view.setTextColor(color)
}

@BindingAdapter("bind:visible")
fun booleanVisibility(view: View, visible: Boolean) {

    if (view is FloatingActionButton) {
        booleanFabVisibility(view, visible)
        return
    }

    if (visible) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

private fun booleanFabVisibility(fab: FloatingActionButton, visible: Boolean) {
    if (visible) {
        fab.show()
    } else {
        fab.hide()
    }
}