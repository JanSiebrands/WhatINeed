package de.siebrands.whatineed.todo.presentation

import de.siebrands.whatineed.todo.application.TodoList

data class TodoListsState(
    val todoLists: List<TodoList> = listOf()
)