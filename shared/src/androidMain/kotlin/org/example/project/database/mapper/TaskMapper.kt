package org.example.project.database.mapper

import org.example.project.data.model.Priority
import org.example.project.data.model.Task
import org.example.project.database.entity.TaskEntity

fun TaskEntity.toTask(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        priority = Priority.valueOf(priority),
        isCompleted = isCompleted,
        createdAt = createdAt
    )
}

fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        description = description,
        priority = priority.name,
        isCompleted = isCompleted,
        createdAt = createdAt
    )
}