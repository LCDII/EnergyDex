package com.example.energydex.presentation.tag.tag_create_edit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.ErrorRed
import com.example.energydex.core.presentation.PrimaryOrange
import com.example.energydex.core.presentation.PrimaryPurple
import com.example.energydex.core.presentation.SecondaryOrange
import com.example.energydex.core.presentation.SecondaryPurple
import com.example.energydex.core.presentation.TagColorValues
import com.example.energydex.core.presentation.tagColor

@Composable
fun TagCreateEditScreenRoot(
    viewModel: TagCreateEditViewModel,
    isEdit: Boolean,
    onSaved: () -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(state.isSaved) {
        if (state.isSaved) onSaved()
    }

    TagCreateEditScreen(
        state = state,
        title = if (isEdit) "Edit tag" else "Create tag",
        onBack = onBack,
        onAction = viewModel::onAction
    )
}

@Composable
private fun TagCreateEditScreen(
    state: TagCreateEditState,
    title: String,
    onBack: () -> Unit,
    onAction: (TagCreateEditAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryPurple)
            .statusBarsPadding()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(
                    containerColor = SecondaryPurple,
                    contentColor = AccentWhite
                )
            ) { Text("Back") }
            Text(title, color = AccentWhite, fontSize = 22.sp)
            Box(modifier = Modifier.size(72.dp))
        }

        OutlinedTextField(
            value = state.name,
            onValueChange = { onAction(TagCreateEditAction.OnNameChange(it)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = !state.isNameValid,
            label = { Text("Tag name") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = AccentWhite,
                unfocusedTextColor = SecondaryOrange,
                focusedBorderColor = PrimaryOrange,
                unfocusedBorderColor = SecondaryOrange
            )
        )

        Text("Choose color", color = SecondaryOrange, fontSize = 16.sp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TagColorValues.take(4).forEach { color ->
                ColorChoice(color, state.color) {
                    onAction(TagCreateEditAction.OnColorSelected(color))
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TagColorValues.drop(4).forEach { color ->
                ColorChoice(color, state.color) {
                    onAction(TagCreateEditAction.OnColorSelected(color))
                }
            }
        }

        state.errorMessage?.let { Text(it.asString(), color = ErrorRed) }

        Button(
            onClick = { onAction(TagCreateEditAction.OnSaveClick) },
            enabled = !state.isSaving,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryOrange,
                contentColor = AccentWhite
            )
        ) { Text(if (state.isSaving) "Saving..." else "Save") }
    }
}

@Composable
private fun ColorChoice(
    color: String,
    selectedColor: String,
    onClick: () -> Unit
) {
    val parsed = tagColor(color)
    Box(
        modifier = Modifier
            .size(44.dp)
            .background(parsed, CircleShape)
            .border(
                width = if (color == selectedColor) 3.dp else 1.dp,
                color = if (color == selectedColor) AccentWhite else Color.Transparent,
                shape = CircleShape
            )
            .clickable(onClick = onClick)
    )
}
