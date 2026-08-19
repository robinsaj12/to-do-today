package org.example.project.presentation.event

import org.example.project.data.model.Priority
import org.example.project.data.model.Task

sealed interface TaskEvent {

    data class AddTask(
        val task: Task
    ) : TaskEvent

    data class UpdateTask(
        val task: Task
    ) : TaskEvent

    data class DeleteTask(
        val task: Task
    ) : TaskEvent

    data class ToggleComplete(
        val task: Task
    ) : TaskEvent

    data class TitleChanged(
        val value: String
    ) : TaskEvent

    data class DescriptionChanged(
        val value: String
    ) : TaskEvent

    data class PriorityChanged(
        val priority: Priority
    ) : TaskEvent
    data class SelectTask(
        val task: Task
    ) : TaskEvent

    data object SaveTask : TaskEvent
}