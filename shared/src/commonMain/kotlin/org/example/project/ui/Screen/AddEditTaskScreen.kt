package org.example.project.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.presentation.event.TaskEvent
import org.example.project.presentation.viewmodel.TaskViewModel
import org.example.project.ui.component.TaskForm

@Composable
fun AddEditTaskScreen(
    viewModel: TaskViewModel,
    onSaveClick: () -> Unit
) {

    val state by viewModel.uiState.collectAsState()

    Scaffold {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            TaskForm(
                title = state.title,
                description = state.description,
                priority = state.priority,
                onTitleChanged = {
                    viewModel.onEvent(
                        TaskEvent.TitleChanged(it)
                    )
                },
                onDescriptionChanged = {
                    viewModel.onEvent(
                        TaskEvent.DescriptionChanged(it)
                    )
                },
                onPriorityChanged = {
                    viewModel.onEvent(
                        TaskEvent.PriorityChanged(it)
                    )
                }
            )

            Button(
                onClick = onSaveClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Task")
            }
        }
    }
}