package de.siebrands.whatineed.android.Screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ContentAlpha
import de.siebrands.whatineed.android.R
import de.siebrands.whatineed.todo.application.TodoItem
import de.siebrands.whatineed.todo.presentation.TodoItemsState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(
    todoUiState: TodoItemsState,
    onNewEntry: (String) -> Unit,
    onCheckedChange: (Boolean, TodoItem) -> Unit,
    onShare: () -> Unit,
    onUpButton: () -> Unit,
    onDelete: (TodoItem) -> Unit,
    onEditClicked: (String, TodoItem) -> Unit,
    onDeleteAllDoneEntries: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showNewEntryPopup by remember {
        mutableStateOf(false)
    }

    var showDeleteAllDoneEntriesPopup by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TodosAppBar(
                title = todoUiState.todoList.name,
                onShare = onShare,
                onUpButton = onUpButton,
                onDeleteButtonPressed = { showDeleteAllDoneEntriesPopup = true }
            )
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
        }
    ) {

        LazyColumn(
            modifier = modifier,
            contentPadding = it
        ) {
            items(todoUiState.items) { task ->
                TodoCard(
                    todo = task,
                    onCheckedChange = { isChecked ->
                        onCheckedChange(isChecked, task)
                    },
                    modifier = Modifier
                        .padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        ),
                    onDelete = {
                        onDelete(task)
                    },
                    onEditClicked = { newName ->
                        onEditClicked(newName, task)
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
                onNewEntry(it)
                showNewEntryPopup = false
            }
        )
    }

    if (showDeleteAllDoneEntriesPopup) {
        DeleteDialog(
            onDismiss = {
                showDeleteAllDoneEntriesPopup = false
            },
            onDelete = {
                showDeleteAllDoneEntriesPopup = false
                onDeleteAllDoneEntries()
            },
            title = stringResource(id = R.string.all_done_entries)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodosAppBar(
    title: String,
    onShare: () -> Unit,
    onUpButton: () -> Unit,
    onDeleteButtonPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = title)
        },
        actions = {
            IconButton(onClick = onShare) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "share"
                )
            }
            IconButton(onClick = onDeleteButtonPressed) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = null
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onUpButton) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoCard(
    todo: TodoItem,
    onCheckedChange: (Boolean) -> Unit,
    onDelete: () -> Unit,
    onEditClicked: (String) -> Unit,
    modifier: Modifier = Modifier
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
                    onCheckedChange(!todo.isDone)
                },
                onLongClick = {
                    showContextMenu = true
                }
            )
            .heightIn(min = 64.dp)
            .alpha(if (todo.isDone) ContentAlpha.disabled else 1f),
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
                .padding(8.dp)
        ) {
            Checkbox(
                checked = todo.isDone,
                onCheckedChange = onCheckedChange,
            )
            Text(
                text = todo.name,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
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
            title = todo.name
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
            name = todo.name
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewEntry(
    onDismissRequest: () -> Unit,
    onSave: (String) -> Unit,
    name: String = ""
) {
    var input by remember {
        mutableStateOf(name)
    }
    val focusRequester = remember {
        FocusRequester()
    }
    AlertDialog(
        title = {
            Text(text = stringResource(id = R.string.new_entry))
        },
        text = {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = {
                    Text(text = stringResource(id = R.string.new_entry))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onSave(input)
                    }
                ),
                modifier = Modifier.focusRequester(focusRequester)
            )
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = { onSave(input) }) {
                Text(text = stringResource(id = R.string.save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }
    )
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Preview
@Composable
fun NewEntryPreview() {
    NewEntry(
        onDismissRequest = {},
        onSave = {}
    )
}
