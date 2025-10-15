package com.example.statejetpackcompose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


class WellnessTask(
    val id: Int,
    val label: String,
    initialChecked: Boolean = false
) {
    var checked by mutableStateOf(initialChecked)
}

//////////////////
// Refactor #02 //
//////////////////

//data class WellnessTask(val id: Int, val label: String, val checked: MutableState<Boolean> = mutableStateOf(false))

//////////////////
// Refactor #01 //
//////////////////

//data class WellnessTask(val id: Int, val label: String, var checked: Boolean = false)

//////////////////
// Refactor #00 //
//////////////////

//data class WellnessTask(val id: Int, val label: String)