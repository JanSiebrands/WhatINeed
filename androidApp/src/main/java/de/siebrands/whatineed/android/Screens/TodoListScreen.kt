package de.siebrands.whatineed.android.Screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.siebrands.whatineed.todo.application.TodoList
import de.siebrands.whatineed.todo.presentation.TodoListsState


@Composable
fun MainPage(
    uiState: TodoListsState,
    onListClicked: (TodoList) -> Unit,
    onNewHeader: (String) -> Unit,
    onDelete: (TodoList) -> Unit,
    onEditClicked: (String, TodoList) -> Unit,
    modifier: Modifier = Modifier
) {
    var showNewEntryPopup by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            MainScreenAppBar()
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showNewEntryPopup = true
            }) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = null
                )
            }
        },
        modifier = modifier
    ) {
        LazyColumn(
            contentPadding = it,
        ) {
            items(uiState.todoLists) { list ->
                TodoHeaderCard(
                    onClicked = {
                        onListClicked(list)
                    },
                    title = list.name,
                    modifier = Modifier
                        .padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        ),
                    onDelete = {
                        onDelete(list)
                    },
                    onEditClicked = { newName ->
                        onEditClicked(newName, list)
                    }
                )
            }
        }
    }
    if (showNewEntryPopup) {
        NewEntry(
            onDismissRequest = {
                showNewEntryPopup = false
            },
            onSave = {
                onNewHeader(it)
                showNewEntryPopup = false
            }
        )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenAppBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(text = "What i need")
        },
        modifier = modifier
    )
}

@Preview
@Composable
fun MainScreenAppBarPreview() {
    MainScreenAppBar()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoHeaderCard(
    onClicked: () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    onEditClicked: (String) -> Unit
) {
    var showDeleteEntryPopup by remember {
        mutableStateOf(false)
    }

    var showContextMenu by remember {
        mutableStateOf(false)
    }

    var showEditEntryPopup by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    onClicked()
                },
                onLongClick = {
                    showContextMenu = true
                },
            )
    ) {
        ContextMenu(
            showContextMenu = showContextMenu,
            onDismissContextMenu = { showContextMenu = false },
            onDeleteClicked = {
                showContextMenu = false
                showDeleteEntryPopup = true
            },
            onEditClicked = {
                showContextMenu = false
                showEditEntryPopup = true
            }
        )
        Row(
            modifier = Modifier
                .heightIn(min = 64.dp)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Text(
                text = title,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp),
                textAlign = TextAlign.Justify
            )
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = null,
            )

        }
    }




    if (showDeleteEntryPopup) {
        DeleteDialog(
            onDismiss = {
                showDeleteEntryPopup = false
            },
            onDelete = {
                showDeleteEntryPopup = false
                onDelete()
            },
            title = title
        )
    }

    if (showEditEntryPopup) {
        NewEntry(
            onDismissRequest = {
                showEditEntryPopup = false
            },
            onSave = {
                showEditEntryPopup = false
                onEditClicked(it)
            },
            name = title
        )
    }


}

@Composable
fun DeleteDialog(
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    title: String,
) {
    AlertDialog(
        title = {
            Text(text = "delete")
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDelete) {
                Text(text = "delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "dismiss")
            }
        }
    )
}

@Preview
@Composable
fun TodoHeaderCardPreview() {
    TodoHeaderCard(
        onClicked = {},
        title = "Test",
        onDelete = {},
        onEditClicked = {}
    )
}

@Composable
fun ContextMenu(
    showContextMenu: Boolean,
    onDismissContextMenu: () -> Unit,
    onEditClicked: () -> Unit,
    onDeleteClicked: () -> Unit
) {

    DropdownMenu(
        expanded = showContextMenu,
        onDismissRequest = onDismissContextMenu
    ) {
        DropdownMenuItem(
            text = { Text(text = "test") },
            onClick = onEditClicked,
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = null)
            }
        )
        DropdownMenuItem(
            text = { Text(text = "test") },
            onClick = onDeleteClicked,
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContextMenuPreview() {
    ContextMenu(
        showContextMenu = true,
        onDeleteClicked = {},
        onDismissContextMenu = {},
        onEditClicked = {}
    )
}

