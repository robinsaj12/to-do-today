package org.example.project.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.repository.TaskRepository
import org.example.project.presentation.event.TaskEvent
import org.example.project.presentation.state.TaskUiState
import org.example.project.data.model.Task

class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        TaskUiState(isLoading = true)
    )

    val uiState: StateFlow<TaskUiState> =
        _uiState.asStateFlow()

    init {
        observeTasks()
    }

    private fun observeTasks() {

        viewModelScope.launch {

            repository.getAllTasks().collect { tasks ->

                _uiState.update {
                    it.copy(
                        tasks = tasks,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onEvent(event: TaskEvent) {

        when (event) {

            is TaskEvent.AddTask -> {
                viewModelScope.launch {
                    repository.insertTask(event.task)
                }
            }

            is TaskEvent.UpdateTask -> {
                viewModelScope.launch {
                    repository.updateTask(event.task)
                }
            }

            is TaskEvent.DeleteTask -> {
                viewModelScope.launch {
                    repository.deleteTask(event.task)
                }
            }

            is TaskEvent.ToggleComplete -> {
                viewModelScope.launch {
                    repository.updateTask(
                        event.task.copy(
                            isCompleted = !event.task.isCompleted
                        )
                    )
                }
            }

            is TaskEvent.TitleChanged -> {
                _uiState.update {
                    it.copy(
                        title = event.value
                    )
                }
            }

            is TaskEvent.DescriptionChanged -> {
                _uiState.update {
                    it.copy(
                        description = event.value
                    )
                }
            }

            is TaskEvent.PriorityChanged -> {
                _uiState.update {
                    it.copy(
                        priority = event.priority
                    )
                }
            }
            is TaskEvent.SelectTask -> {

                _uiState.update {
                    it.copy(
                        editingTaskId = event.task.id,
                        title = event.task.title,
                        description = event.task.description,
                        priority = event.task.priority
                    )
                }
            }
            TaskEvent.SaveTask -> {

                val state = _uiState.value

                if (state.title.isBlank()) return

                viewModelScope.launch {

                    if (state.editingTaskId == null) {

                        repository.insertTask(
                            Task(
                                title = state.title,
                                description = state.description,
                                priority = state.priority
                            )
                        )

                    } else {

                        repository.updateTask(
                            Task(
                                id = state.editingTaskId,
                                title = state.title,
                                description = state.description,
                                priority = state.priority
                            )
                        )
                    }

                    _uiState.update {
                        it.copy(
                            title = "",
                            description = "",
                            priority = org.example.project.data.model.Priority.MEDIUM,
                            editingTaskId = null
                        )
                    }
                }
            }
        }
    }
}