package org.example.project

import androidx.compose.runtime.*
import org.example.project.data.model.Task
import org.example.project.presentation.event.TaskEvent
import org.example.project.presentation.viewmodel.TaskViewModel
import org.example.project.theme.TodoTheme
import org.example.project.ui.screen.AddEditTaskScreen
import org.example.project.ui.screen.TaskListScreen

private enum class Screen {
    LIST,
    ADD
}

@Composable
fun App(
    viewModel: TaskViewModel
) {

    var currentScreen by remember {
        mutableStateOf(Screen.LIST)
    }

    val uiState by viewModel.uiState.collectAsState()

    TodoTheme {

        when (currentScreen) {

            Screen.LIST -> {

                TaskListScreen(
                    viewModel = viewModel,
                    onAddTaskClick = {
                        currentScreen = Screen.ADD
                    }
                )
            }

            Screen.ADD -> {

                AddEditTaskScreen(
                    title = uiState.title,
                    description = uiState.description,
                    priority = uiState.priority,
                    isEditing = uiState.editingTaskId != null,

                    onTitleChange = {
                        viewModel.onEvent(
                            TaskEvent.TitleChanged(it)
                        )
                    },

                    onDescriptionChange = {
                        viewModel.onEvent(
                            TaskEvent.DescriptionChanged(it)
                        )
                    },

                    onPrioritySelected = {
                        viewModel.onEvent(
                            TaskEvent.PriorityChanged(it)
                        )
                    },

                    onSaveClick = {

                        viewModel.onEvent(
                            TaskEvent.SaveTask
                        )

                        currentScreen = Screen.LIST
                    }

                )
            }
        }
    }
}

//
//
//package org.example.project
//
//import androidx.compose.runtime.Composable
//import org.example.project.presentation.viewmodel.TaskViewModel
//import org.example.project.theme.TodoTheme
//import org.example.project.ui.screen.TaskListScreen
//
//@Composable
//fun App(
//    viewModel: TaskViewModel
//) {
//
//    TodoTheme {
//
//        TaskListScreen(
//            viewModel = viewModel,
//            onAddTaskClick = {
//                println("FAB Clicked")
//            }
//        )
//    }
//}
