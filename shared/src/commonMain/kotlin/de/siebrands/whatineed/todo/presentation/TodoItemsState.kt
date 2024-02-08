package de.siebrands.whatineed.todo.presentation

import de.siebrands.whatineed.todo.application.TodoItem
import de.siebrands.whatineed.todo.application.TodoList

data class TodoItemsState(
    val todoList: TodoList = TodoList(0, ""),
    val items: List<TodoItem> = listOf()
)