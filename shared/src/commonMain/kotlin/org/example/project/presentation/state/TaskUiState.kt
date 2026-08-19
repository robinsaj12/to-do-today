package org.example.project.presentation.state

import org.example.project.data.model.Priority
import org.example.project.data.model.Task

data class TaskUiState(
    val tasks: List<Task> = emptyList(),

    val isLoading: Boolean = false,

    val title: String = "",

    val description: String = "",

    val priority: Priority = Priority.MEDIUM
)