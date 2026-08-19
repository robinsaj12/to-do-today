package org.example.project.presentation.state

import org.example.project.data.model.Priority
import org.example.project.data.model.Task
import org.example.project.data.model.TaskFilter
import org.example.project.data.model.TaskSort

data class TaskUiState(
    val tasks: List<Task> = emptyList(),

    val isLoading: Boolean = false,

    val searchQuery: String = "",

    val selectedFilter: TaskFilter = TaskFilter.ALL,

    val selectedSort: TaskSort = TaskSort.NEWEST,

    val title: String = "",
    val description: String = "",
    val priority: Priority = Priority.MEDIUM,

    val selectedTaskId: Long? = null,

    val isEditMode: Boolean = false
)