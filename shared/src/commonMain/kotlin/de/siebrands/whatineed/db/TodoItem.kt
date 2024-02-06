package de.siebrands.whatineed.db

data class TodoItem(
    val id: Int,
    val name: String,
    val isDone: Boolean
)
