package com.combah.dindin2.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.combah.dindin2.data.Movement
import com.combah.dindin2.databinding.MovementListItemBinding
import com.combah.dindin2.util.LiveList
import com.combah.dindin2.viewmodel.MovementViewModel

class MovementsAdapter(lifecycleOwner: LifecycleOwner, private val movements: LiveList<Movement>)
    : RecyclerView.Adapter<BindingViewHolder<MovementListItemBinding>>() {

    init {
        movements.observe(lifecycleOwner, ListUpdateAdapterCallback(this))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<MovementListItemBinding> {
        val binding = MovementListItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return BindingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movements.size
    }

    override fun onBindViewHolder(holder: BindingViewHolder<MovementListItemBinding>, position: Int) {
        holder.binding.viewModel = MovementViewModel(movements[position])
    }

}