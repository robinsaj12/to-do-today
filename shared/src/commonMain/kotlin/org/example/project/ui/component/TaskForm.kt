package org.example.project.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.data.model.Priority

@Composable
fun TaskForm(
    title: String,
    description: String,
    priority: Priority,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onPriorityChanged: (Priority) -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        OutlinedTextField(
            value = title,
            onValueChange = onTitleChanged,
            label = { androidx.compose.material3.Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChanged,
            label = { androidx.compose.material3.Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        PrioritySelector(
            selected = priority,
            onPrioritySelected = onPriorityChanged
        )
    }
}