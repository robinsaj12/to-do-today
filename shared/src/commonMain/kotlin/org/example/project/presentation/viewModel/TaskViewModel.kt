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
import org.example.project.data.model.TaskFilter
import org.example.project.data.model.TaskSort
import org.example.project.data.model.Priority

class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    private fun applySorting(
        tasks: List<Task>,
        sort: TaskSort
    ): List<Task> {

        return when (sort) {

            TaskSort.NEWEST ->
                tasks.sortedByDescending {
                    it.createdAt
                }

            TaskSort.OLDEST ->
                tasks.sortedBy {
                    it.createdAt
                }

            TaskSort.HIGH_PRIORITY ->
                tasks.sortedBy {
                    when (it.priority) {
                        Priority.HIGH -> 0
                        Priority.MEDIUM -> 1
                        Priority.LOW -> 2
                    }
                }

            TaskSort.LOW_PRIORITY ->
                tasks.sortedBy {
                    when (it.priority) {
                        Priority.LOW -> 0
                        Priority.MEDIUM -> 1
                        Priority.HIGH -> 2
                    }
                }
        }
    }

    private fun applyFilters(
        tasks: List<Task>,
        query: String,
        filter: TaskFilter
    ): List<Task> {

        var result = tasks

        if (query.isNotBlank()) {

            result = result.filter {

                it.title.contains(
                    query,
                    ignoreCase = true
                ) ||

                        it.description.contains(
                            query,
                            ignoreCase = true
                        )
            }
        }

        result = when (filter) {

            TaskFilter.ALL -> result

            TaskFilter.COMPLETED ->
                result.filter { it.isCompleted }

            TaskFilter.PENDING ->
                result.filter { !it.isCompleted }

            TaskFilter.HIGH ->
                result.filter {
                    it.priority.name == "HIGH"
                }

            TaskFilter.MEDIUM ->
                result.filter {
                    it.priority.name == "MEDIUM"
                }

            TaskFilter.LOW ->
                result.filter {
                    it.priority.name == "LOW"
                }
        }

        return applySorting(
            result,
            _uiState.value.selectedSort
        )
    }

    private var allTasks: List<Task> = emptyList()

    private fun filterTasks(
        tasks: List<Task>,
        query: String
    ): List<Task> {

        if (query.isBlank()) {
            return tasks
        }

        return tasks.filter {

            it.title.contains(
                query,
                ignoreCase = true
            ) ||
                    it.description.contains(
                        query,
                        ignoreCase = true
                    )
        }
    }

    private val _uiState = MutableStateFlow(
        TaskUiState(isLoading = true)
    )

    val uiState: StateFlow<TaskUiState> =
        _uiState.asStateFlow()

    init {
        observeTasks()
    }

//    private fun observeTasks() {
//
//        viewModelScope.launch {
//
//            repository.getAllTasks().collect { tasks ->
//
//                _uiState.update {
//                    it.copy(
//                        tasks = filterTasks(
//                            tasks,
//                            _uiState.value.searchQuery
//                        ),
//                        isLoading = false
//                    )
//                }
//            }
//        }
//    }
    //Fix
private fun observeTasks() {

    viewModelScope.launch {

        repository.getAllTasks().collect { tasks ->

            allTasks = tasks

            _uiState.update {
                it.copy(
                    tasks = applyFilters(
                        tasks,
                        it.searchQuery,
                        it.selectedFilter
                    ),
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
            is TaskEvent.SearchChanged -> {

                _uiState.update {
                    it.copy(
                        searchQuery = event.query,
                        tasks = applyFilters(
                            allTasks,
                            event.query,
                            it.selectedFilter
                        )
                    )
                }
            }
            is TaskEvent.FilterChanged -> {

                _uiState.update {
                    it.copy(
                        selectedFilter = event.filter,
                        tasks = applyFilters(
                            allTasks,
                            it.searchQuery,
                            event.filter
                        )
                    )
                }
            }

            is TaskEvent.SortChanged -> {

                _uiState.update {
                    it.copy(
                        selectedSort = event.sort,
                        tasks = applySorting(
                            applyFilters(
                                allTasks,
                                it.searchQuery,
                                it.selectedFilter
                            ),
                            event.sort
                        )
                    )
                }
            }

        }
    }
}