package com.combah.travel.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.combah.dindin2.util.LiveList
import java.util.function.Predicate
import java.util.function.UnaryOperator

class LiveMutableList<E>(private val arrayList: ArrayList<E> = ArrayList(), diffCallback: DiffCallback<E>? = null) : LiveList<E>(diffCallback), List<E> by arrayList {

    init {
        value = arrayList
    }

    public override fun setValue(value: List<E>?) {
        super.setValue(value)
    }

    operator fun set(index: Int, element: E): E {
        return doAndUpdate { arrayList.set(index, element) }
    }

    fun add(e: E): Boolean {
        return doAndUpdate { arrayList.add(e) }
    }

    fun add(index: Int, element: E) {
        return doAndUpdate { arrayList.add(index, element) }
    }

    fun removeAt(index: Int): E {
        return doAndUpdate { arrayList.removeAt(index) }
    }

    fun remove(o: E): Boolean {
        return doAndUpdate { arrayList.remove(o) }
    }

    fun clear() {
        return doAndUpdate { arrayList.clear() }
    }

    fun addAll(c: Collection<E>): Boolean {
        return doAndUpdate { arrayList.addAll(c) }
    }

    fun addAll(index: Int, c: Collection<E>): Boolean {
        return doAndUpdate { arrayList.addAll(index, c) }
    }

    fun removeAll(c: Collection<*>): Boolean {
        return doAndUpdate { arrayList.removeAll(c) }
    }

    fun retainAll(c: Collection<*>): Boolean {
        return doAndUpdate { arrayList.retainAll(c) }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun removeIf(filter: Predicate<in E>): Boolean {
        return doAndUpdate { arrayList.removeIf(filter) }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun replaceAll(operator: UnaryOperator<E>) {
        return doAndUpdate { arrayList.replaceAll(operator) }
    }

    fun update(index: Int, transformation: E.() -> Unit) {
        return doAndUpdate {
            arrayList[index].transformation()
        }
    }

    fun findAndUpdate(predicate: (E) -> Boolean, transformation: E.() -> Unit) {
        return doAndUpdate {
            arrayList.find(predicate)?.transformation()
        }
    }

    override val size: Int
        get() = super.size

    private fun <T> doAndUpdate(block: () -> T): T {
        val ret = block()
        postValue(arrayList)
        return ret
    }
}