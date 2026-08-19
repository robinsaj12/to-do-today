package org.example.project.ui.component

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*

@Composable
fun FilterMenu(
    filters: List<String>,
    selected: String,
    onSelected: (String) -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    androidx.compose.material3.TextButton(
        onClick = {
            expanded = true
        }
    ) {
        Text(selected)
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {
            expanded = false
        }
    ) {

        filters.forEach { filter ->

            DropdownMenuItem(
                text = {
                    Text(filter)
                },
                onClick = {
                    expanded = false
                    onSelected(filter)
                }
            )
        }
    }
}