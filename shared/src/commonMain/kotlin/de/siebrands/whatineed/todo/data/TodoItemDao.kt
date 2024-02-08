package de.siebrands.whatineed.todo.data

data class TodoItemDao(
    val id: Long,
    val name: String,
    val isDone: Boolean
)
