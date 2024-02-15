package de.siebrands.whatineed.todo.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import de.siebrands.whatineed.db.WhatINeedDatabase
import de.siebrands.whatineed.todo.application.TodoList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow

class TodoDataSource(private val whatINeedDatabase: WhatINeedDatabase) {

    fun getTodoLists(): Flow<List<TodoList>> {
        return whatINeedDatabase.whatINeedDatabaseQueries.selectTodoLists(::mapToTodoList)
            .asFlow()
            .mapToList(Dispatchers.IO)
    }


    fun insertTodoList(newList: String) {
        whatINeedDatabase.whatINeedDatabaseQueries.transaction {
            whatINeedDatabase.whatINeedDatabaseQueries.insertTodoList(
                name = newList
            )
        }
    }

    fun deleteTodoList(id: Long) {
        whatINeedDatabase.whatINeedDatabaseQueries.deleteTodoList(id)
    }

    fun updateTodoLost(id: Long, newName: String) {
        whatINeedDatabase.whatINeedDatabaseQueries.updateTodoList(newName, id)
    }

    private fun mapToTodoList(
        id: Long,
        name: String,
    ): TodoList =
        TodoList(
            id,
            name
        )
}