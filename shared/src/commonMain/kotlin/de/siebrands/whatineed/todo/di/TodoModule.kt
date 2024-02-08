package de.siebrands.whatineed.todo.di

import de.siebrands.whatineed.todo.data.TodoDataSource
import de.siebrands.whatineed.todo.data.TodoRepository
import de.siebrands.whatineed.todo.presentation.TodoItemsViewModel
import de.siebrands.whatineed.todo.presentation.TodoListsViewModel
import org.koin.dsl.module

val todoModule = module {

    single<TodoDataSource> { TodoDataSource(get()) }
    single<TodoRepository> { TodoRepository(get()) }
    single<TodoListsViewModel> { TodoListsViewModel(get()) }
    single<TodoItemsViewModel> { TodoItemsViewModel() }
}