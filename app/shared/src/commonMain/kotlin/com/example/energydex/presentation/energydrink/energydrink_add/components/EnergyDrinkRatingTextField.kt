package com.example.energydex.presentation.energydrink.energydrink_add.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
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
import com.example.energydex.core.presentation.ErrorRed
import com.example.energydex.core.presentation.PrimaryOrange
import com.example.energydex.core.presentation.SecondaryOrange
import com.example.energydex.core.presentation.SecondaryPurple
import energydex.app.shared.generated.resources.Res
import energydex.app.shared.generated.resources.energy_drink_rating
import energydex.app.shared.generated.resources.ic_gem
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun EnergyDrinkRatingTextField(
    ratingText: String,
    onRatingChange: (String) -> Unit,
    isValid: Boolean,
    modifier: Modifier = Modifier
) {

    OutlinedTextField(
        value = ratingText,
        onValueChange = onRatingChange,
        shape = RoundedCornerShape(8.dp),
        placeholder = {
            Text(
                text = stringResource(Res.string.energy_drink_rating),
                color = SecondaryOrange,
            ) },
        isError = !isValid,
        singleLine = true,
        modifier = modifier
            .widthIn(50.dp)
            .background(
                color = SecondaryPurple,
                shape = RoundedCornerShape(8.dp),
            ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = PrimaryOrange,
            unfocusedTextColor = SecondaryOrange,
            cursorColor = AccentWhite,
            errorTextColor = ErrorRed,
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
            leadingIcon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_gem),
                    contentDescription = null,
                    tint = AccentWhite
                    )
                },
            )
}

@Preview
@Composable
fun PreviewEnergyDrinkRatingTextFieldEmpty() {
    EnergyDrinkRatingTextField(
        ratingText = "",
        onRatingChange = {},
        isValid = true,

        )
}

@Preview
@Composable
fun PreviewEnergyDrinkRatingTextField() {
    EnergyDrinkRatingTextField(
        ratingText = "2.0",
        onRatingChange = {},
        isValid = true,

        )
}