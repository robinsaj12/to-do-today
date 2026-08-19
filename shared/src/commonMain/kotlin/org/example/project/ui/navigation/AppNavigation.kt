package org.example.project.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.project.presentation.viewmodel.TaskViewModel
import org.example.project.ui.screen.AddEditTaskScreen
import org.example.project.ui.screen.TaskListScreen

@Composable
fun AppNavigation(
    viewModel: TaskViewModel
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.TaskList.route
    ) {

        composable(
            route = Screen.TaskList.route
        ) {

            TaskListScreen(
                viewModel = viewModel,
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
                viewModel = viewModel,
                onSaveClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}


