package org.example.project.ui.component

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*

@Composable
fun SortMenu(
    sorts: List<String>,
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

        sorts.forEach { sort ->

            DropdownMenuItem(
                text = {
                    Text(sort)
                },
                onClick = {
                    expanded = false
                    onSelected(sort)
                }
            )
        }
    }
}