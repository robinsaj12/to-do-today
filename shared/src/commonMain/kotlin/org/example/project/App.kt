package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.example.project.ui.screen.TaskListScreen
import org.jetbrains.compose.resources.painterResource

import to_do_today.shared.generated.resources.Res
import to_do_today.shared.generated.resources.compose_multiplatform


import org.example.project.data.repository.FakeTaskRepository
import org.example.project.presentation.viewmodel.TaskViewModel
import org.example.project.theme.TodoTheme

@Composable
fun App() {

    val viewModel = remember {
        TaskViewModel(
            FakeTaskRepository()
        )
    }

    TodoTheme {

        TaskListScreen(
            viewModel = viewModel,
            onAddTaskClick = {}
        )
    }
}