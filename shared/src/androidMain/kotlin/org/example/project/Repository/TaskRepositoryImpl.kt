package org.example.project.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.project.data.model.Task
import org.example.project.data.repository.TaskRepository
import org.example.project.database.dao.TaskDao
import org.example.project.database.mapper.toEntity
import org.example.project.database.mapper.toTask

class TaskRepositoryImpl(
    private val taskDao: TaskDao
) : TaskRepository {

    override fun getAllTasks(): Flow<List<Task>> {

        return taskDao
            .getAllTasks()
            .map { entityList ->

                entityList.map {
                    it.toTask()
                }
            }
    }

    override suspend fun insertTask(
        task: Task
    ) {
        taskDao.insertTask(
            task.toEntity()
        )
    }

    override suspend fun updateTask(
        task: Task
    ) {
        taskDao.updateTask(
            task.toEntity()
        )
    }

    override suspend fun deleteTask(
        task: Task
    ) {
        taskDao.deleteTask(
            task.toEntity()
        )
    }
}