package org.example.project.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.example.project.data.repository.TaskRepository
import org.example.project.presentation.event.TaskEvent
import org.example.project.presentation.state.TaskUiState
import org.example.project.data.model.Task
import org.example.project.data.model.Priority
import org.example.project.data.model.TaskFilter
import org.example.project.data.model.TaskSort

class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    private fun applyFilters(
        tasks: List<Task>
    ): List<Task> {

        val state = _uiState.value

        var result = tasks

        if (state.searchQuery.isNotBlank()) {

            result = result.filter {
                it.title.contains(
                    state.searchQuery,
                    ignoreCase = true
                )
            }
        }

        result = when (state.selectedFilter) {

            TaskFilter.ALL -> result

            TaskFilter.COMPLETED ->
                result.filter { it.isCompleted }

            TaskFilter.PENDING ->
                result.filter { !it.isCompleted }

            TaskFilter.HIGH ->
                result.filter { it.priority == Priority.HIGH }

            TaskFilter.MEDIUM ->
                result.filter { it.priority == Priority.MEDIUM }

            TaskFilter.LOW ->
                result.filter { it.priority == Priority.LOW }
        }

        result = when (state.selectedSort) {

            TaskSort.NEWEST ->
                result.sortedByDescending { it.createdAt }

            TaskSort.OLDEST ->
                result.sortedBy { it.createdAt }

            TaskSort.HIGH_PRIORITY ->
                result.sortedBy {
                    when (it.priority) {
                        Priority.HIGH -> 0
                        Priority.MEDIUM -> 1
                        Priority.LOW -> 2
                    }
                }

            TaskSort.LOW_PRIORITY ->
                result.sortedBy {
                    when (it.priority) {
                        Priority.LOW -> 0
                        Priority.MEDIUM -> 1
                        Priority.HIGH -> 2
                    }
                }
        }

        return result
    }

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
                        tasks = applyFilters(tasks),
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
            //new features

            is TaskEvent.TitleChanged -> {
                _uiState.value = _uiState.value.copy(
                    title = event.value
                )
            }

            is TaskEvent.DescriptionChanged -> {
                _uiState.value = _uiState.value.copy(
                    description = event.value
                )
            }

            is TaskEvent.PriorityChanged -> {
                _uiState.value = _uiState.value.copy(
                    priority = event.priority
                )
            }

            is TaskEvent.SaveTask -> {

                val currentState = _uiState.value

                if (currentState.title.isBlank()) {
                    return
                }

                viewModelScope.launch {

                    repository.insertTask(
                        Task(
                            title = currentState.title,
                            description = currentState.description,
                            priority = currentState.priority
                        )
                    )

                    _uiState.update {
                        it.copy(
                            title = "",
                            description = "",
                            priority = Priority.MEDIUM
                        )
                    }
                }
            }

            is TaskEvent.ClearForm -> {

                _uiState.update {
                    it.copy(
                        title = "",
                        description = "",
                        priority = Priority.MEDIUM
                    )
                }
            }
            is TaskEvent.SearchChanged -> {

                _uiState.update {
                    it.copy(
                        searchQuery = event.query
                    )
                }
            }

            is TaskEvent.FilterChanged -> {

                _uiState.update {
                    it.copy(
                        selectedFilter = event.filter
                    )
                }
            }

            is TaskEvent.SortChanged -> {

                _uiState.update {
                    it.copy(
                        selectedSort = event.sort
                    )
                }
            }

            is TaskEvent.SelectTask -> {

                _uiState.update {
                    it.copy(
                        selectedTaskId = event.task.id,
                        title = event.task.title,
                        description = event.task.description,
                        priority = event.task.priority
                    )
                }
            }
        }
    }
}

