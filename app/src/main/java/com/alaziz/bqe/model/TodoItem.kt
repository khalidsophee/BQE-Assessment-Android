// Created by Khalid Aziz
package com.alaziz.bqe.model

data class TodoItem(
    val id: Int,
    val title: String,
    val description: String = "",
    var isDone: Boolean = false
)