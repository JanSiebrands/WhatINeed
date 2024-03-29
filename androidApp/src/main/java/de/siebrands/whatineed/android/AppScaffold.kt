package de.siebrands.whatineed.android

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.siebrands.whatineed.android.Screens.MainPage
import de.siebrands.whatineed.android.Screens.Screens
import de.siebrands.whatineed.android.Screens.TodoScreen
import de.siebrands.whatineed.todo.presentation.TodoItemsState
import de.siebrands.whatineed.todo.presentation.TodoListsViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun AppScaffold() {
    val navController = rememberNavController()

    Scaffold {
        AppNavHost(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        )
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Main.route,
        modifier = modifier
    ) {
        composable(route = Screens.Main.route) {
            val viewModel: TodoListsViewModel = getViewModel()
            val state by viewModel.todoListState.collectAsState()
            MainPage(
                uiState = state,
                onListClicked = {
                    navController.navigate(Screens.Items.route)
                },
                onNewHeader = {
                    viewModel.newTodoList(it)
                },
                onDelete = {
                    viewModel.deleteTodoList(it)
                },
                onEditClicked = { newName, list ->
                    viewModel.updateTodoListName(newName, list)
                },
            )
        }
        composable(route = Screens.Items.route) {
            TodoScreen(
                todoUiState = TodoItemsState(),
                onNewEntry = {},
                onCheckedChange = { a, b -> },
                onShare = { /*TODO*/ },
                onUpButton = { /*TODO*/ },
                onDelete = {},
                onEditClicked = { a, b -> },
                onDeleteAllDoneEntries = { /*TODO*/ })
        }
    }
}