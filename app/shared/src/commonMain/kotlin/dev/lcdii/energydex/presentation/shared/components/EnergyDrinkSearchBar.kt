package dev.lcdii.energydex.presentation.shared.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndSelectAll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.drop
import energydex.app.shared.generated.resources.Res
import energydex.app.shared.generated.resources.close_hint
import energydex.app.shared.generated.resources.ic_close
import energydex.app.shared.generated.resources.ic_search
import energydex.app.shared.generated.resources.search_hint
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import dev.lcdii.energydex.core.presentation.AccentWhite

@Composable
fun EnergyDrinkSearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onImeSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    val textFieldState = rememberTextFieldState()

    LaunchedEffect(searchQuery) {
        if (textFieldState.text.toString() != searchQuery) {
            textFieldState.setTextAndSelectAll(searchQuery)
        }
    }

    LaunchedEffect(textFieldState) {
        snapshotFlow { textFieldState.text.toString() }
            .drop(1)
            .collect { onSearchQueryChange(it) }
    }

    OutlinedTextField(
        state = textFieldState,
        shape = RoundedCornerShape(100),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 13.dp),
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = AccentWhite,
            focusedBorderColor = AccentWhite
        ),
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = AccentWhite),
        placeholder = {
            Text(
                text = stringResource(Res.string.search_hint),
                color = AccentWhite
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(Res.drawable.ic_search),
                contentDescription = null,
                tint = AccentWhite
            )
        },
        lineLimits = TextFieldLineLimits.SingleLine,
        onKeyboardAction = { onImeSearch() },
        trailingIcon = {
            AnimatedVisibility(
                visible = searchQuery.isNotBlank()
            ) {
                IconButton(
                    onClick = {
                        onSearchQueryChange("")
                    }
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_close),
                        contentDescription = stringResource(Res.string.close_hint),
                        tint = AccentWhite
                    )
                }
            }
        },
        modifier = modifier
            .minimumInteractiveComponentSize()
    )
}

@Preview
@Composable
fun PreviewEnergyDrinkSearchBar() {
            EnergyDrinkSearchBar(
                searchQuery = "",
                onSearchQueryChange = {},
                onImeSearch = {}
            )
}