

package org.example.project.ui.navigation

sealed class Screen(
    val route: String
) {

    data object TaskList : Screen(
        route = "task_list"
    )

    data object AddEditTask : Screen(
        route = "add_edit_task"
    )
}