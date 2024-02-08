package de.siebrands.whatineed.todo.presentation

import de.siebrands.whatineed.BaseViewModel
import de.siebrands.whatineed.todo.data.TodoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TodoListsViewModel(private val todoRepository: TodoRepository) : BaseViewModel() {

    init {

    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    fun insertNewList() {
        todoRepository.newTodoList("test")
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