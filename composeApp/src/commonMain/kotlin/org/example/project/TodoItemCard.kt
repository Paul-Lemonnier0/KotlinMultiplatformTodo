package org.example.project

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import contrastColor
import org.example.project.models.TodoItem
import secondaryColor
import ui.theme.RegularText
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@OptIn(ExperimentalMaterialApi::class, ExperimentalUuidApi::class)
@Composable
fun TodoItemCard(
    item: TodoItem,
    selectItem: (String) -> Unit,
    isItemSelected: (String) -> Boolean,
    toggleTodoItem: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        elevation = 0.dp,
        shape = RoundedCornerShape(15.dp),
        onClick = { selectItem(item.id) },
        border = BorderStroke(
            width = 2.dp,
            color = if (isItemSelected(item.id)) contrastColor else secondaryColor
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = item.isDone,
                onCheckedChange = {
                    toggleTodoItem(item.id)
                }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = item.title, style = RegularText())
        }
    }
}