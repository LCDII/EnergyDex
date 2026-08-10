package com.example.energydex.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.SecondaryPurple
import energydex.composeapp.generated.resources.Res
import energydex.composeapp.generated.resources.ic_energy_drinks_list_view_longed
import energydex.composeapp.generated.resources.ic_energy_drinks_list_view_squared
import org.jetbrains.compose.resources.painterResource

@Composable
fun EnergyDrinkViewModeToggle(
    modifier: Modifier = Modifier,
    selectedTab: EnergyDrinkSectionTab,
    onTabSelected: (EnergyDrinkSectionTab) -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, SecondaryPurple, RoundedCornerShape(12.dp)),
    ) {
        IconToggleButton(
            checked = selectedTab == EnergyDrinkSectionTab.SQUARED,
            onCheckedChange = { onTabSelected(EnergyDrinkSectionTab.SQUARED) },
            modifier = Modifier
                .weight(1f)
                .background(
                    color = if (selectedTab == EnergyDrinkSectionTab.SQUARED)
                        SecondaryPurple else Color.Transparent,
                    shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
                )
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_energy_drinks_list_view_squared),
                contentDescription = "Squared",
                tint = AccentWhite,
                modifier = Modifier.size(24.dp)
            )
        }
        IconToggleButton(
            checked = selectedTab == EnergyDrinkSectionTab.LONGED,
            onCheckedChange = { onTabSelected(EnergyDrinkSectionTab.LONGED) },
            modifier = Modifier
                .weight(1f)
                .background(
                    color = if (selectedTab == EnergyDrinkSectionTab.LONGED)
                        SecondaryPurple else Color.Transparent,
                    shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
                )
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_energy_drinks_list_view_longed),
                contentDescription = "Longed",
                tint = AccentWhite,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
