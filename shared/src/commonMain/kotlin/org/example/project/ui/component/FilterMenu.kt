package org.example.project.ui.component

import androidx.compose.material3.*
import androidx.compose.runtime.*
import org.example.project.data.model.TaskFilter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterMenu(
    selected: TaskFilter,
    onSelected: (TaskFilter) -> Unit
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
            label = { Text("Filter") }
        )

        ExposedDropdownMenu(
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
}