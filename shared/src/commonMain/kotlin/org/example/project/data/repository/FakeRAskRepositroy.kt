package org.example.project.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.example.project.data.model.Priority
import org.example.project.data.model.Task

class FakeTaskRepository : TaskRepository {

    private val tasks = MutableStateFlow(
        listOf(
            Task(
                id = 1,
                title = "Buy Groceries",
                description = "Milk, Bread, Eggs",
                priority = Priority.HIGH
            ),
            Task(
                id = 2,
                title = "Workout",
                description = "1 hour gym session",
                priority = Priority.MEDIUM
            ),
            Task(
                id = 3,
                title = "Read Book",
                description = "Read 20 pages",
                priority = Priority.LOW
            ),
            Task(
                id = 4,
                title = "Complete KMP ToDo App",
                description = "Finish Phase 6 UI",
                priority = Priority.HIGH,
                isCompleted = true
            )
        )
    )

    override fun getAllTasks(): Flow<List<Task>> = tasks

    override suspend fun insertTask(task: Task) {
        tasks.value = tasks.value + task
    }

    override suspend fun updateTask(task: Task) {
        tasks.value = tasks.value.map {
            if (it.id == task.id) task else it
        }
    }

    override suspend fun deleteTask(task: Task) {
        tasks.value = tasks.value.filterNot {
            it.id == task.id
        }
    }
}