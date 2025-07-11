// Created by Khalid Aziz
package com.alaziz.bqe.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alaziz.bqe.model.TodoItem

@Composable
fun TodoApp() {
    var todoList by remember { mutableStateOf(listOf<TodoItem>()) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var idCounter by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description (Optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (title.isNotBlank()) {
                    todoList = todoList + TodoItem(idCounter++, title, description)
                    title = ""
                    description = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add To-Do")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Divider()

        LazyColumn {
            items(todoList) { item ->
                TodoItemRow(
                    item = item,
                    onToggle = {
                        todoList = todoList.map {
                            if (it.id == item.id) it.copy(isDone = !it.isDone) else it
                        }
                    },
                    onDelete = {
                        todoList = todoList.filterNot { it.id == item.id }
                    }
                )
                Divider()
            }
        }
    }
}