package de.siebrands.whatineed.todo.presentation

import de.siebrands.whatineed.BaseViewModel
import de.siebrands.whatineed.todo.application.TodoList
import de.siebrands.whatineed.todo.data.TodoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TodoListsViewModel(private val todoRepository: TodoRepository) : BaseViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    fun newTodoList(name: String) {
        todoRepository.newTodoList(name);
    }

    fun updateTodoListName(newName: String, todoList: TodoList) {
        todoRepository.updateTodoList(todoList.id, newName)
    }

    fun deleteTodoList(todoList: TodoList) {
        todoRepository.deleteTodoList(todoList.id)
    }

    val todoListState: StateFlow<TodoListsState> =
        todoRepository.getTodoLists()
            .filterNotNull()
            .map {
                TodoListsState(it)
            }
            .stateIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = TodoListsState()
            )
}