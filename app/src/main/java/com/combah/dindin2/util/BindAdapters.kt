package com.combah.dindin2.util

import android.databinding.BindingAdapter
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.combah.dindin2.R
import java.util.*

@BindingAdapter("android:textColor")
fun setTextColor(view: TextView, resId: Int) {
    val color = ContextCompat.getColor(view.context, resId)
    view.setTextColor(color)
}

@BindingAdapter("currencyValue")
fun setCurrencyValue(view: TextView, value: Double?) {
    view.text = value?.asCurrency()
}

@BindingAdapter("valueColor")
fun valueForColor(view: TextView, value: Double?) {
    if (value == null) {
        return
    }
    setTextColor(view, when {
        value > 0 -> R.color.income
        value < 0 -> R.color.expense
        else -> R.color.textColor
    })
}

@BindingAdapter("booleanColor")
fun booleanForColor(view: TextView, value: Boolean?) {
    if (value == null) {
        return
    }
    setTextColor(view, if (value) {
        R.color.income
    } else {
        R.color.expense
    })
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

@BindingAdapter("android:text")
fun setDateText(view: TextView, date: Date?) {
    date?.let { view.text = it.dateString() }
}