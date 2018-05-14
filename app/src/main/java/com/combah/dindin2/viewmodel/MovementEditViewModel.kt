package com.combah.dindin2.viewmodel

import android.arch.lifecycle.ViewModel
import com.combah.dindin2.data.Movement
import com.combah.dindin2.repository.MovementRepository
import java.util.*
import javax.inject.Inject

class MovementEditViewModel @Inject constructor(private val movementRepository: MovementRepository) : ViewModel() {

    var date = Date()
    var income = false
    var description: String? = null

    private var _value = 0.0
    var valueString
        get() = String.format("%.2f", _value)
        set(value) {
            _value = value.toDoubleOrNull() ?: 0.0
        }


    fun setMovement(movement: Movement) {
        date = movement.date
        income = movement.income
        description = movement.description
        _value = movement.value
    }

    fun save() {
        movementRepository.saveMovement(Movement(
                date = date,
                income = income,
                description = description,
                value = _value)
        )
    }

}