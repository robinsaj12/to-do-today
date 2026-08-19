package org.example.project.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.project.data.model.Priority
import org.example.project.data.repository.FakeTaskRepository
import org.example.project.presentation.viewmodel.TaskViewModel
import org.example.project.ui.screen.AddEditTaskScreen
import org.example.project.ui.screen.TaskListScreen

@Composable
fun AppNavigation() {
    val viewModel = remember {
        TaskViewModel(
            repository = FakeTaskRepository()
        )
    }

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.TaskList.route
    ) {

        composable(
            route = Screen.TaskList.route
        ) {

            TaskListScreen(viewModel=viewModel,
                onAddTaskClick = {
                    navController.navigate(
                        Screen.AddEditTask.route
                    )
                }
            )
        }

        composable(
            route = Screen.AddEditTask.route
        ) {

            AddEditTaskScreen(
                title = "",
                description = "",
                priority = Priority.MEDIUM,
                onTitleChange = {},
                onDescriptionChange = {},
                onPrioritySelected = {},
                onSaveClick = {
                    navController.popBackStack()
                },
                isEditing=true,
            )
        }
    }
}