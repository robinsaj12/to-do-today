package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.example.project.database.DatabaseFactory
import org.example.project.presentation.viewmodel.TaskViewModel
import org.example.project.repository.TaskRepositoryImpl

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val database = DatabaseFactory.create(this)

        val repository = TaskRepositoryImpl(
            database.taskDao()
        )

        val viewModel = TaskViewModel(
            repository = repository
        )

        setContent {

            App(
                viewModel = viewModel
            )
        }
    }
}