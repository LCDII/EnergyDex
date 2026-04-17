package com.example.energydex.presentation.energydrink.energydrink_section.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import energydex.composeapp.generated.resources.Res
import energydex.composeapp.generated.resources.close_hint
import energydex.composeapp.generated.resources.ic_close
import energydex.composeapp.generated.resources.ic_search
import energydex.composeapp.generated.resources.search_hint
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun EnergyDrinkSearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onImeSearch: () -> Unit,
    modifier: Modifier = Modifier
) {

    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        shape = RoundedCornerShape(100),
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = Color(0xFFFFFFFF),
            focusedBorderColor = Color(0xFFFFFFFF)
        ),
        placeholder = {
            Text(
                text = stringResource(Res.string.search_hint),
                color = Color(0xFFFFFFFF)
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(Res.drawable.ic_search),
                contentDescription = null,
                tint = Color(0xFFFFFFFF)
            )
        },
        singleLine = true,
        keyboardActions = KeyboardActions(
            onSearch ={
                onImeSearch()
            }
        ),
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
                        tint = Color(0xFFFFFFFF)
                    )
                }
            }
        },
        modifier = modifier
            .background(
                shape = RoundedCornerShape(100),
                color = Color(0x73995EFF)
            )
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