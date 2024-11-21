package org.example.project.models

import androidx.compose.runtime.mutableStateListOf
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import org.example.project.createHttpClient

@Serializable
data class AddTodoItemRequest(val title: String)

class TodoList {

    val items = mutableStateListOf<TodoItem>()

    val selectedItems = mutableStateListOf<String>()

    suspend fun addItem(item: TodoItem) {
        items.add(item)
    }

    fun removeItem(id: String) {
        items.removeAll { it.id == id }
    }

    fun removeSelectedItems() {
        items.removeAll { selectedItems.contains(it.id) }
        selectedItems.clear()
    }

    fun toggleItemCompletion(id: String): Boolean {
        val todo = items.find { it.id == id }
        todo?.let {
            val index = items.indexOf(it)
            items[index] = it.copy(isDone = !it.isDone)
            return items[index].isDone
        }

        return false
    }

    fun isSelected(id: String): Boolean = selectedItems.any { it == id }

    fun selectItem(id: String) {
        if (isSelected(id)) {
            selectedItems.remove(id)
        } else {
            selectedItems.add(id)
        }
    }

    fun hasSelectedItems(): Boolean = selectedItems.isNotEmpty()

    fun doneRatio(): Float {
        val doneCount = items.count { it.isDone }
        val totalCount = items.size
        return if (totalCount > 0) {
            (doneCount.toDouble() / totalCount * 100).toFloat()
        } else {
            0.0f
        }
    }
}
