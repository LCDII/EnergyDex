package dev.lcdii.energydex.presentation.tag.tag_create_edit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import dev.lcdii.energydex.core.presentation.AccentWhite
import dev.lcdii.energydex.core.presentation.AppBackground
import dev.lcdii.energydex.core.presentation.GlassPanelTint
import dev.lcdii.energydex.core.presentation.GreenGradientVertical
import dev.lcdii.energydex.core.presentation.TagColorValues
import dev.lcdii.energydex.core.presentation.glassContainer
import dev.lcdii.energydex.core.presentation.tagGradient
import dev.lcdii.energydex.presentation.shared.components.GlassBackButton
import dev.lcdii.energydex.presentation.shared.components.GlassCircleButton
import com.kashif_e.backdrop.backdrops.rememberCanvasBackdrop
import energydex.app.shared.generated.resources.Res
import energydex.app.shared.generated.resources.ic_check
import org.jetbrains.compose.resources.painterResource

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
    val backdrop = rememberCanvasBackdrop { drawRect(AppBackground) }
    val textShape = RoundedCornerShape(100)

    Box(modifier = Modifier.fillMaxSize().background(AppBackground)) {
        GlassBackButton(
            backdrop = backdrop,
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .statusBarsPadding()
                .padding(start = 8.dp, top = 12.dp)
        )

        Text(
            text = title,
            color = AccentWhite,
            fontSize = 22.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = 16.dp)
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = 70.dp)
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .glassContainer(
                    backdrop = backdrop,
                    shape = textShape,
                    tint = GlassPanelTint
                )
        ) {
            OutlinedTextField(
                value = state.name,
                onValueChange = { onAction(TagCreateEditAction.OnNameChange(it)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = !state.isNameValid,
                placeholder = { Text("Tag name", color = AccentWhite.copy(alpha = 0.5f)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                shape = textShape,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = AccentWhite,
                    unfocusedTextColor = AccentWhite,
                    cursorColor = AccentWhite,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )
        }

        Text(
            text = "Choose color",
            color = AccentWhite.copy(alpha = 0.6f),
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = 136.dp, start = 8.dp)
                .fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = 176.dp)
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp), // 2 ряда по 44.dp + отступ 12.dp
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(TagColorValues) { color ->
                    ColorChoice(
                        color = color,
                        selectedColor = state.color,
                        onClick = { onAction(TagCreateEditAction.OnColorSelected(color)) }
                    )
                }
            }
        }

        state.errorMessage?.let {
            Text(
                text = it.asString(),
                color = Color.Red,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .statusBarsPadding()
                    .padding(top = 290.dp, start = 8.dp)
                    .fillMaxWidth()
            )
        }

        GlassCircleButton(
            onClick = { onAction(TagCreateEditAction.OnSaveClick) },
            contentDescription = "Save",
            icon = Res.drawable.ic_check,
            backdrop = backdrop,
            tintBrush = GreenGradientVertical,
            iconTint = Color.Black,
            size = 80.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        )
    }
}

@Composable
private fun ColorChoice(
    color: String,
    selectedColor: String,
    onClick: () -> Unit
) {
    val gradient = tagGradient(color)
    Box(
        modifier = Modifier
            .size(44.dp)
            .background(brush = gradient, shape = CircleShape)
            .border(
                width = if (color == selectedColor) 3.dp else 1.dp,
                color = if (color == selectedColor) AccentWhite else Color.Transparent,
                shape = CircleShape
            )
            .clickable(onClick = onClick)
    )
}