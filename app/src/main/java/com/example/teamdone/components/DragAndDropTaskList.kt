package com.example.teamdone.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun DragAndDropTaskList() {
    var backlogTasks by remember { mutableStateOf(listOf("Task 1")) }
    var inProgressTasks by remember { mutableStateOf(listOf("Task 2")) }
    var completedTasks by remember { mutableStateOf(listOf("Task 3")) }
    var draggedItem by remember { mutableStateOf<String?>(null) }

    Column {
        DragAndDropGroup(
            items = backlogTasks,
            onItemDragged = { draggedItem = it },
            onItemDropped = {
                draggedItem?.let { item ->
                    backlogTasks = backlogTasks.toMutableList().apply { remove(item) }
                    inProgressTasks = inProgressTasks + item
                    draggedItem = null
                }
            }
        )
        DragAndDropGroup(
            items = inProgressTasks,
            onItemDragged = { draggedItem = it },
            onItemDropped = {
                draggedItem?.let { item ->
                    inProgressTasks = inProgressTasks.toMutableList().apply { remove(item) }
                    backlogTasks = backlogTasks + item
                    draggedItem = null
                }
            }
        )
    }
}

@Composable
fun DragAndDropGroup(
    items: List<String>,
    onItemDragged: (String) -> Unit,
    onItemDropped: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items.forEach { item ->
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .background(Color.White)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { onItemDragged(item) },
                            onDragEnd = { onItemDropped() },
                            onDragCancel = {},
                            onDrag = { x, y ->

                            }
                        )
                    }
                    .padding(8.dp)
            )
        }
    }
}