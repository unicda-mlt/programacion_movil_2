package com.example.statejetpackcompose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableIntStateOf(0) }
    StatelessCounter(count, { count++ }, modifier)
}

@Composable
fun StatelessCounter(count: Int, onIncrement: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = onIncrement, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}

//////////////////
// Refactor #07 //
//////////////////

//@Composable
//fun WaterCounter(modifier: Modifier = Modifier) {
//    Column(modifier = modifier.padding(16.dp)) {
//        var count by rememberSaveable { mutableIntStateOf(0) }
//
//        if (count > 0) {
//            Text("You've had $count glasses.")
//        }
//        Button(onClick = { count++ }, Modifier.padding(top = 8.dp), enabled = count < 10) {
//            Text("Add one")
//        }
//    }
//}

//////////////////
// Refactor #06 //
//////////////////

//@Composable
//fun WaterCounter(modifier: Modifier = Modifier) {
//    Column (modifier = modifier.padding(16.dp)) {
//        var count by remember { mutableIntStateOf(0) }
//        if (count > 0) {
//            var showTask by remember { mutableStateOf(true) }
//            if (showTask) {
//                WellnessTaskItem(
//                    onClose = { showTask = false },
//                    taskName = "Have you taken your 15 minute walk today?"
//                )
//            }
//            Text("You've had $count glasses.")
//        }
//
//        Row (Modifier.padding(top = 8.dp)) {
//            Button(onClick = { count++ }, enabled = count < 10) {
//                Text("Add one")
//            }
//            Button(
//                onClick = { count = 0 },
//                Modifier.padding(start = 8.dp)) {
//                Text("Clear water count")
//            }
//        }
//    }
//}

//////////////////
// Refactor #05 //
//////////////////

//@Composable
//fun WaterCounter(modifier: Modifier = Modifier) {
//    Column(modifier = Modifier.padding(16.dp)) {
//        var count by remember { mutableStateOf(0) }
//        if (count > 0) {
//            var showTask by remember { mutableStateOf(true) }
//            if (showTask) {
//                WellnessTaskItem(
//                    onClose = { },
//                    taskName = "Have you taken your 15 minute walk today?"
//                )
//            }
//            Text("You've had $count glasses.")
//        }
//
//        Button(onClick = { count++ }, enabled = count < 10) {
//            Text("Add one")
//        }
//    }
//}

//////////////////
// Refactor #04 //
//////////////////

//@Composable
//fun WaterCounter(modifier: Modifier = Modifier) {
//    var count by remember { mutableStateOf(0) }
//
//    Column(modifier = modifier.padding(16.dp)) {
//        Text("You've had ${count} glasses.")
//        Button(onClick = { count++ }, Modifier.padding(top = 8.dp), enabled = count < 10) {
//            Text("Add one")
//        }
//    }
//}

//////////////////
// Refactor #03 //
//////////////////

//@Composable
//fun WaterCounter(modifier: Modifier = Modifier) {
//    val count: MutableState<Int> = remember { mutableStateOf(0) }
//
//    Column(modifier = modifier.padding(16.dp)) {
//        Text("You've had ${count.value} glasses.")
//        Button(onClick = { count.value++ }, Modifier.padding(top = 8.dp)) {
//            Text("Add one")
//        }
//    }
//}

//////////////////
// Refactor #02 //
//////////////////

//@SuppressLint("UnrememberedMutableState")
//@Composable
//fun WaterCounter(modifier: Modifier = Modifier) {
//    Column(modifier = modifier.padding(16.dp)) {
//        // Changes to count are now tracked by Compose
//        val count: MutableState<Int> = mutableStateOf(0)
//
//        Text("You've had ${count.value} glasses.")
//        Button(onClick = { count.value++ }, Modifier.padding(top = 8.dp)) {
//            Text("Add one")
//        }
//    }
//}

//////////////////
// Refactor #01 //
//////////////////

//@Composable
//fun WaterCounter(modifier: Modifier = Modifier) {
//    Column (modifier = modifier.padding(16.dp)) {
//        var count = 0
//        Text("You've had $count glasses.")
//        Button (onClick = { count++ }, Modifier.padding(top = 8.dp)) {
//            Text("Add one")
//        }
//    }
//}

//////////////////
// Refactor #00 //
//////////////////

//@Composable
//fun WaterCounter(modifier: Modifier = Modifier) {
//    val count = 0
//    Text(
//        text = "You've had $count glasses.",
//        modifier = modifier.padding(16.dp)
//    )
//}