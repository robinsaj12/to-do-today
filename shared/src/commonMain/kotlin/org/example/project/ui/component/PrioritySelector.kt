package org.example.project.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import org.example.project.data.model.Priority

@Composable
fun PrioritySelector(
    selectedPriority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Priority.entries.forEach { priority ->

            androidx.compose.material3.FilterChip(
                selected = selectedPriority == priority,
                onClick = {
                    onPrioritySelected(priority)
                },
                label = {
                    androidx.compose.material3.Text(
                        priority.name
                    )
                }
            )
        }
    }
}