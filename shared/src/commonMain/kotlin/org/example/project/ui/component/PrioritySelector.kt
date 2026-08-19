package org.example.project.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import org.example.project.data.model.Priority

@Composable
fun PrioritySelector(
    selected: Priority,
    onPrioritySelected: (Priority) -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Priority.entries.forEach { priority ->

            FilterChip(
                selected = selected == priority,
                onClick = {
                    onPrioritySelected(priority)
                },
                label = {
                    Text(priority.name)
                }
            )
        }
    }
}