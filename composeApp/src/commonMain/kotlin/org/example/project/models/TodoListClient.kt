package org.example.project.models

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.parameters
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

class TodoListClient(private val httpClient: HttpClient) {


    suspend fun fetchTodoItems(): List<TodoItem> {
        val response: HttpResponse = try {
            httpClient.get("http://10.0.2.2:8080/list") {
                headers {
                    append("Accept", "application/json")
                    append("Content-Type", "application/json")
                }
            }
        } catch (e: UnresolvedAddressException) {
            println("Error: Unable to resolve address: ${e.message}")
            return emptyList()
        } catch (e: Exception) {
            println("Error: Unexpected exception: ${e.message}")
            return emptyList()
        }

        return when (response.status) {
            HttpStatusCode.OK -> {
                try {
                    response.body<List<TodoItem>>() // Parse JSON response
                } catch (e: SerializationException) {
                    println("Error: Failed to deserialize response: ${e.message}")
                    emptyList()
                }
            }
            HttpStatusCode.Unauthorized -> {
                println("Error: Unauthorized access (401).")
                emptyList()
            }
            else -> {
                println("Error: Unexpected response status ${response.status.value}.")
                emptyList()
            }
        }
    }

    suspend fun addTodoItem(item: TodoItem): HttpResponse {
        return httpClient.post("http://10.0.2.2:8080/add") {
            headers {
                append("Accept", "application/json")
                append("Content-Type", "application/json")
            }

            setBody(
                """{
                    "id": "${item.id}",
                    "title": "${item.title}"
                }"""
            )
        }
    }

    suspend fun removeTodoItem(id: String): HttpResponse {
        return httpClient.post("http://10.0.2.2:8080/delete") {
            headers {
                append("Accept", "application/json")
                append("Content-Type", "application/json")
            }

            setBody(
                """{
                    "id": "$id"
                }"""
            )
        }
    }

    suspend fun toggleTodoItem(id: String, isDone: Boolean): HttpResponse {
        return httpClient.post("http://10.0.2.2:8080/update") {
            headers {
                append("Accept", "application/json")
                append("Content-Type", "application/json")
            }

            setBody(
                """{
                    "id": "$id",
                    "isDone": "$isDone"
                }"""
            )
        }
    }
}