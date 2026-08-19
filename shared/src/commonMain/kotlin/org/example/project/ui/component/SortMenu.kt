package org.example.project.ui.component

import androidx.compose.material3.*
import androidx.compose.runtime.*
import org.example.project.data.model.TaskSort

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortMenu(
    selected: TaskSort,
    onSelected: (TaskSort) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {

        OutlinedTextField(
            value = selected.name,
            onValueChange = {},
            readOnly = true,
            label = { Text("Sort") }
        )

        ExposedDropdownMenu(
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
}