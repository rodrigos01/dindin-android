package com.combah.dindin2.viewmodel

import com.combah.dindin2.data.Movement

class MovementViewModel(movement: Movement) {
    val date = movement.date
    val income = movement.income
    val description = movement.description
    val value = movement.value
}