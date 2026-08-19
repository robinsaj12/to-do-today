package org.example.project.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.presentation.event.TaskEvent
import org.example.project.presentation.viewmodel.TaskViewModel
import org.example.project.ui.component.EmptyState
import org.example.project.ui.component.TaskItem

@Composable
fun TaskListScreen(
    viewModel: TaskViewModel,
    onAddTaskClick: () -> Unit
) {

    val state by viewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTaskClick
            ) {
                Text("+")
            }
        }
    ) { paddingValues ->

        if (state.tasks.isEmpty()) {

            EmptyState()

        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp)
            ) {

                items(
                    items = state.tasks,
                    key = { it.id }
                ) { task ->

                    TaskItem(
                        task = task,
                        onToggleComplete = {
                            viewModel.onEvent(
                                TaskEvent.ToggleComplete(task)
                            )
                        },
                        onDelete = {
                            viewModel.onEvent(
                                TaskEvent.DeleteTask(task)
                            )
                        }
                    )

                    Divider()
                }
            }
        }
    }
}