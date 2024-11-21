package org.example.project

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Colors
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.SheetState
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import contrastColor
import fieldColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import secondaryColor
import ui.theme.HugeText
import ui.theme.RegularText
import ui.theme.TitleText

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AddTodoBottomSheet(
    showBottomSheet: MutableState<Boolean>,
    scope: CoroutineScope,
    sheetState: SheetState,
    handleValidation: (String) -> Unit
) {
    var text = remember { mutableStateOf("") }
    var isFocused = remember { mutableStateOf(false) }

    fun validate() {
        handleValidation(text.value)
        text.value = ""
        showBottomSheet.value = false
    }

    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet.value = false // Hide bottom sheet on dismiss
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 15.dp)
        ) {

            Text("Add Todo", style = TitleText())
            OutlinedTextField(
                value = text.value,
                onValueChange = { text.value = it },
                label = {
                    if (!isFocused.value) {
                        Text("Enter a todo item", style = RegularText(isGray = true))
                    }
                },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        isFocused.value = focusState.isFocused
                    },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = contrastColor, // Couleur de la bordure lorsque le champ est en focu
                    unfocusedBorderColor = secondaryColor, // Couleur de la bordure lorsque le champ n'est pas en focus
                    cursorColor = contrastColor, // Couleur du curseur
                    focusedLabelColor = secondaryColor, // Couleur du label lorsque le champ est en focus
                    unfocusedLabelColor = fieldColor, // Couleur du label lorsque le champ n'est pas en focus
                ),
                textStyle = RegularText(),
                singleLine = true, // Optionnel : limite le champ à une seule ligne
                shape = RoundedCornerShape(8.dp) // Coins arrondis
            )
            androidx.compose.material.Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            onClick = { validate() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0, 0, 0),
                    contentColor = Color.White,
                    disabledBackgroundColor = Color.Gray,
                    disabledContentColor = Color.LightGray
                )
            ) {
                androidx.compose.material.Text("Ajouter", style = RegularText(isContrast = true))
            }
        }


    }
}