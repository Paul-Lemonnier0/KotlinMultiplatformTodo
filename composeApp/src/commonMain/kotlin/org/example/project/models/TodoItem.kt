package org.example.project.models

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

import androidx.compose.runtime.mutableStateOf
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlinx.serialization.Serializable

@Serializable
data class TodoItemResponse(
    val id: String,
    val title: String,
    val isDone: Boolean,
    val creationDate: String
)

@Serializable
data class TodoItem @OptIn(ExperimentalUuidApi::class) constructor(
    val id: String,
    val title: String,
    var isDone: Boolean = false,
    val creationDate: String
)