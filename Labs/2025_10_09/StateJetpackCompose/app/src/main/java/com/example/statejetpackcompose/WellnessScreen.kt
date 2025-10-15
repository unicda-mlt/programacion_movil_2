package com.example.statejetpackcompose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    wellnessViewModel: WellnessViewModel = viewModel()
) {
    Column(modifier = modifier) {
        StatefulCounter()

        WellnessTasksList(
            list = wellnessViewModel.tasks,
            onCheckedTask = { task, checked ->
                wellnessViewModel.changeTaskChecked(task, checked)
            },
            onCloseTask = { task ->
                wellnessViewModel.remove(task)
            }
        )
    }
}

//////////////////
// Refactor #03 //
//////////////////

//@Composable
//fun WellnessScreen(
//    modifier: Modifier = Modifier,
//    wellnessViewModel: WellnessViewModel = viewModel()
//) {
//    Column(modifier = modifier) {
//        StatefulCounter()
//
//        WellnessTasksList(
//            list = wellnessViewModel.tasks,
//            onCloseTask = { task -> wellnessViewModel.remove(task) })
//    }
//}

//////////////////
// Refactor #02 //
//////////////////

//@Composable
//fun WellnessScreen(modifier: Modifier = Modifier) {
//    Column(modifier = modifier) {
//        StatefulCounter()
//
//        val list = remember { getWellnessTasks().toMutableStateList() }
//        WellnessTasksList(list = list, onCloseTask = { task -> list.remove(task) })
//    }
//}
//
//private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }

//////////////////
// Refactor #01 //
//////////////////

//@Composable
//fun WellnessScreen(modifier: Modifier = Modifier) {
//    Column(modifier = modifier) {
//        StatefulCounter()
//        WellnessTasksList()
//    }
//}

//////////////////
// Refactor #00 //
//////////////////

//@Composable
//fun WellnessScreen(modifier: Modifier = Modifier) {
//    StatefulCounter(modifier)
//}