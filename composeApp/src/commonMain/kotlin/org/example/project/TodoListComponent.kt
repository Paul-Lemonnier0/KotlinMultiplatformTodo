package org.example.project

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import org.example.project.models.TodoList
import org.example.project.models.TodoListClient
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalMaterialApi::class, ExperimentalUuidApi::class)
@Composable
fun TodoListSection(
    todoList: TodoList,
    handleToggleTodo: (id: String, isDone: Boolean) -> Unit
) {
    val selectItem: (String) -> Unit = { id -> todoList.selectItem(id) }
    val toggleTodoItem: (String) -> Unit = { id ->
        val isDone = todoList.toggleItemCompletion(id)
        handleToggleTodo(id, isDone)
    }
    val isItemSelected: (String) -> Boolean = { id -> todoList.isSelected(id) }

    LazyColumn {
        items(todoList.items) { item ->
            TodoItemCard(
                item = item,
                selectItem = selectItem,
                isItemSelected = isItemSelected,
                toggleTodoItem = toggleTodoItem
            )
        }
    }
}