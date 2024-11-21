package org.example.project

import CustomTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

import androidx.compose.runtime.saveable.rememberSaveable

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.project.models.TodoItem
import org.example.project.models.TodoList
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.theme.HugeText
import ui.theme.RegularText
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

import contrastColor
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import secondaryColor

import kotlinx.coroutines.launch
import org.example.project.models.TodoListClient

@OptIn(ExperimentalMaterialApi::class, ExperimentalUuidApi::class, ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App(
    client: TodoListClient
) {
    val todoList = TodoList()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                todoList.items.addAll(client.fetchTodoItems())
            } catch (e: Throwable) {
                println("Error fetching todo items: ${e.message}")
            }
        }
    }

    val addNewItem: suspend (String) -> Unit = { text ->
        val newItem = TodoItem(
            id = Uuid.random().toString(),
            title = text,
            creationDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
        )

        client.addTodoItem(newItem)
        todoList.addItem(newItem)
    }

    val handleValidation: (String) -> Unit = { text ->
        scope.launch {
            try {
                addNewItem(text)
                print("Added !")
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun hasSelectedItems() = todoList.hasSelectedItems()

    suspend fun removeItem(id: String) {
        client.removeTodoItem(id)
        todoList.removeItem(id)
    }

    fun removeSelectedItems() {
        scope.launch {
            try {
                todoList.selectedItems.forEach { id -> removeItem(id) }
            }

            catch (e: Throwable) {
                println("Error: ${e.message}")
            }
        }
    }

    fun toggleTodoItem(id: String, isDone: Boolean) {
        scope.launch {
            try {
                client.toggleTodoItem(id, isDone)
            } catch (e: Throwable) {
                println("Error: ${e.message}")
            }
        }
    }

    val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val today = currentDateTime.date.toString()

    val sheetState = androidx.compose.material3.rememberModalBottomSheetState()
    var showBottomSheet = rememberSaveable { mutableStateOf(false) }

    fun toggleModal() {
        showBottomSheet.value = !showBottomSheet.value
    }

    CustomTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            HeaderSection(today)
            ProgressSection(todoList.doneRatio())

            ActionButtonsSection(
                addNewItem = ::toggleModal,
                removeSelectedItems = ::removeSelectedItems,
                hasSelectedItems = hasSelectedItems()
            )
            TodoListSection(todoList) { id: String, isDone: Boolean -> toggleTodoItem(id, isDone) }
            if(showBottomSheet.value) {
                AddTodoBottomSheet(
                    showBottomSheet = showBottomSheet,
                    scope = scope,
                    sheetState = sheetState,
                    handleValidation = { text -> handleValidation(text) }
                )
            }
        }
    }
}



@Composable
fun HeaderSection(today: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("todo.", style = HugeText())
        Text(today, style = RegularText(isGray = true))
    }
}

@Composable
fun ProgressSection(progress: Float = 0.0f) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .weight(1f)
                .padding(top = 20.dp, bottom = 10.dp),
            strokeCap = StrokeCap.Round,
            color = contrastColor,
            backgroundColor = secondaryColor,
            progress = progress / 100
        )
        Text("$progress%", style = RegularText())
    }
}

@Composable
fun ActionButtonsSection(
    addNewItem: () -> Unit,
    removeSelectedItems: () -> Unit,
    hasSelectedItems: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = addNewItem,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0, 0, 0),
                contentColor = Color.White,
                disabledBackgroundColor = Color.Gray,
                disabledContentColor = Color.LightGray
            )
        ) {
            Text("add", style = RegularText(isContrast = true))
        }
        Button(
            onClick = removeSelectedItems,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0, 0, 0),
                contentColor = Color.White,
                disabledBackgroundColor = Color.Gray,
                disabledContentColor = Color.LightGray
            ),
            enabled = hasSelectedItems
        ) {
            Text("update", style = RegularText(isContrast = true))
        }
    }
}
