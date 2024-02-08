package de.siebrands.whatineed.todo.data

import de.siebrands.whatineed.todo.application.TodoList
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoDataSource: TodoDataSource) {
    fun getTodoLists(): Flow<List<TodoList>> {
        return todoDataSource.getTodoLists()
    }

    fun newTodoList(name: String) {
        todoDataSource.insertTodoList(name)
    }
}