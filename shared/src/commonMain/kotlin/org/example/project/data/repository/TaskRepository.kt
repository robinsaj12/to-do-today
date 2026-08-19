package org.example.project.data.repository

import kotlinx.coroutines.flow.Flow
import org.example.project.data.model.Task

interface TaskRepository {

    fun getAllTasks(): Flow<List<Task>>

    suspend fun insertTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun deleteTask(task: Task)
}