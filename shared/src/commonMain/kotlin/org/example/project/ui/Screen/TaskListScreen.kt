package org.example.project.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.presentation.event.TaskEvent
import org.example.project.presentation.viewmodel.TaskViewModel
import org.example.project.ui.component.EmptyState
import org.example.project.ui.component.SearchBar
import org.example.project.ui.component.TaskItem
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import org.example.project.ui.component.FilterMenu
import org.example.project.ui.component.SortMenu

@Composable
fun TaskListScreen(
    viewModel: TaskViewModel,
    onAddTaskClick: () -> Unit
) {

    val state by viewModel.uiState.collectAsState()

    Scaffold(


        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTaskClick
            ) {
                Text(
                            text = "+"
                )
            }
        }


    ) { paddingValues ->

        if (state.tasks.isEmpty()) {

            EmptyState()

        } else {
            SearchBar(
                query = state.searchQuery,
                onQueryChange = {
                    viewModel.onEvent(
                        TaskEvent.SearchChanged(it)
                    )
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                FilterMenu(
                    selected = state.selectedFilter,
                    onSelected = {
                        viewModel.onEvent(
                            TaskEvent.FilterChanged(it)
                        )
                    }
                )

                SortMenu(
                    selected = state.selectedSort,
                    onSelected = {
                        viewModel.onEvent(
                            TaskEvent.SortChanged(it)
                        )
                    }
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp)
            ) {

                items(
                    items = state.tasks,
                    key = { it.id }
                ) { task ->

                    TaskItem(
                        task = task,
                        onToggleComplete = {
                            viewModel.onEvent(
                                TaskEvent.ToggleComplete(task)
                            )
                        },
                        onDelete = {
                            viewModel.onEvent(
                                TaskEvent.DeleteTask(task)
                            )
                        }
                    )

                    Divider()

                }
            }
        }
    }
}