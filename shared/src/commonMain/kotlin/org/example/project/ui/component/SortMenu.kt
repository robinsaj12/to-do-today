package org.example.project.ui.component

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import org.example.project.data.model.TaskSort

@Composable
fun SortMenu(
    selected: TaskSort,
    onSelected: (TaskSort) -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    TextButton(
        onClick = {
            expanded = true
        }
    ) {
        Text(selected.name)
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {
            expanded = false
        }
    ) {

        TaskSort.entries.forEach { sort ->

            DropdownMenuItem(
                text = {
                    Text(sort.name)
                },
                onClick = {
                    expanded = false
                    onSelected(sort)
                }
            )
        }
    }
}