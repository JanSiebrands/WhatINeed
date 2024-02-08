package de.siebrands.whatineed.todo.application

data class TodoItem(
    val id: Long,
    val name: String,
    val isDone: Boolean
)
