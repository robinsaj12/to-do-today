package org.example.project.ui.component

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import org.example.project.data.model.TaskFilter

@Composable
fun FilterMenu(
    selected: TaskFilter,
    onSelected: (TaskFilter) -> Unit
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

        TaskFilter.entries.forEach { filter ->

            DropdownMenuItem(
                text = {
                    Text(filter.name)
                },
                onClick = {
                    expanded = false
                    onSelected(filter)
                }
            )
        }
    }
}