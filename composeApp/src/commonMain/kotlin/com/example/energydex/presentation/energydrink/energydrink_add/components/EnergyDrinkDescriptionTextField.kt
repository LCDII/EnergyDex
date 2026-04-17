package com.example.energydex.presentation.energydrink.energydrink_add.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.PrimaryOrange
import com.example.energydex.core.presentation.SecondaryOrange
import com.example.energydex.core.presentation.SecondaryPurple
import energydex.composeapp.generated.resources.Res
import energydex.composeapp.generated.resources.close_hint
import energydex.composeapp.generated.resources.energy_drink_description
import energydex.composeapp.generated.resources.ic_close
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun EnergyDrinkDescriptionTextField(
    description: String,
    onDescriptionChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = description,
        onValueChange = onDescriptionChange,
        shape = RoundedCornerShape(8.dp),
        textStyle = TextStyle(
            letterSpacing = 0.5.sp,
            fontSize = 18.sp
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = PrimaryOrange,
            unfocusedTextColor = SecondaryOrange,
            cursorColor = AccentWhite,
            focusedBorderColor = AccentWhite
        ),
        placeholder = {
            Text(
                text = stringResource(Res.string.energy_drink_description),
                color = SecondaryOrange,
            ) },
        minLines = 3,
        maxLines = 5,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(300.dp)
            .background(
                color = SecondaryPurple,
                shape = RoundedCornerShape(8.dp),
            ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        trailingIcon = {
            AnimatedVisibility(
                visible =  description.isNotBlank()
            ) {
                IconButton(
                    onClick = {
                        onDescriptionChange("")
                    },
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
fun PreviewEnergyDrinkDescriptionTextFieldEmpty() {
    EnergyDrinkDescriptionTextField(
        description = "",
        onDescriptionChange = {}
        )
}

@Preview
@Composable
fun PreviewEnergyDrinkDescriptionTextField() {
    EnergyDrinkDescriptionTextField(
        description = "Aboba",
        onDescriptionChange = {}
    )
}