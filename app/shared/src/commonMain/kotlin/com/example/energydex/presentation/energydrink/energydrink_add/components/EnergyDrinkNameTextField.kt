package com.example.energydex.presentation.energydrink.energydrink_add.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import energydex.app.shared.generated.resources.Res
import energydex.app.shared.generated.resources.close_hint
import energydex.app.shared.generated.resources.energy_drink_name
import energydex.app.shared.generated.resources.energy_drink_name_required
import energydex.app.shared.generated.resources.ic_close
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import com.example.energydex.core.presentation.PrimaryOrange
import com.example.energydex.core.presentation.SecondaryPurple
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.SecondaryOrange
import com.example.energydex.core.presentation.ErrorRed

@Composable
fun EnergyDrinkNameTextField(
    name: String,
    onNameChange: (String) -> Unit,
    isValid: Boolean,
    modifier: Modifier = Modifier
) {

    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        shape = RoundedCornerShape(8.dp),
        placeholder = {
            Text(
                text = stringResource(Res.string.energy_drink_name),
                color = SecondaryOrange,
            ) },
        isError = !isValid,
        supportingText = if (!isValid) {
            {
                Text(stringResource(Res.string.energy_drink_name_required))
            }
        } else {
            null
        },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = SecondaryPurple,
                shape = RoundedCornerShape(8.dp),
            ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = PrimaryOrange,
            unfocusedTextColor = SecondaryOrange,
            cursorColor = AccentWhite,
            focusedBorderColor = if (isValid)
                AccentWhite
            else
                ErrorRed,
        ),
        textStyle = TextStyle(
            letterSpacing = 0.5.sp,
            fontSize = 18.sp
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        trailingIcon = {
            AnimatedVisibility(
                visible = name.isNotBlank()
            ) {
                IconButton(
                    onClick = {
                        onNameChange("")
                    }
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_close),
                        contentDescription = stringResource(Res.string.close_hint),
                        tint = SecondaryOrange
                    )
                }
            }
        },


    )
}

@Preview
@Composable
fun PreviewEnergyDrinkNameTextFieldEmpty() {
    EnergyDrinkNameTextField(
        name = "Aboba",
        onNameChange = {},
        isValid = true,

    )
}

@Preview
@Composable
fun PreviewEnergyDrinkNameTextField() {
    EnergyDrinkNameTextField(
        name = "",
        onNameChange = {},
        isValid = true,

        )
}
