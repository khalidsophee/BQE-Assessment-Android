// Created by Khalid Aziz
package com.alaziz.bqe.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.alaziz.bqe.model.TodoItem

@Composable
fun TodoItemRow(item: TodoItem, onToggle: () -> Unit, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .testTag("todo_row_${item.id}"),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                modifier = Modifier.testTag("title_${item.id}"),
                style = TextStyle(
                    textDecoration = if (item.isDone) TextDecoration.LineThrough else TextDecoration.None
                )
            )
            if (item.description.isNotEmpty()) {
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Row {
            Checkbox(
                checked = item.isDone,
                onCheckedChange = { onToggle() },
                modifier = Modifier.testTag("checkbox_${item.id}")
            )
            IconButton(
                onClick = onDelete,
                modifier = Modifier.testTag("delete_${item.id}")
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}
