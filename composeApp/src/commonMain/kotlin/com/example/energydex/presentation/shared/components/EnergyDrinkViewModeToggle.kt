package com.example.energydex.presentation.shared.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.energydex.core.presentation.AccentWhite
import com.example.energydex.core.presentation.GlassChipTint
import com.example.energydex.core.presentation.TabGradient
import com.example.energydex.core.presentation.TextOnGradient
import com.example.energydex.core.presentation.glassContainer
import com.example.energydex.core.presentation.glassThumb
import com.kashif_e.backdrop.Backdrop
import energydex.composeapp.generated.resources.Res
import energydex.composeapp.generated.resources.ic_energy_drinks_list_view_longed
import energydex.composeapp.generated.resources.ic_energy_drinks_list_view_squared
import org.jetbrains.compose.resources.painterResource

@Composable
fun EnergyDrinkViewModeToggle(
    backdrop: Backdrop,
    selectedTab: EnergyDrinkSectionTab,
    onTabSelected: (EnergyDrinkSectionTab) -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .width(120.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(28.dp))
            .glassContainer(
                backdrop = backdrop,
                shape = RoundedCornerShape(28.dp),
                tint = GlassChipTint
            )
    ) {
        val halfWidth = maxWidth / 2
        val targetOffset = if (selectedTab == EnergyDrinkSectionTab.LONGED) halfWidth else 0.dp
        val thumbOffset by animateDpAsState(
            targetValue = targetOffset,
            animationSpec = tween(durationMillis = 250),
            label = "toggleThumbOffset"
        )

        val thumbShape = RoundedCornerShape(percent = 50)
        Box(
            modifier = Modifier
                .offset(x = thumbOffset)
                .width(halfWidth)
                .fillMaxHeight()
                .clip(thumbShape)
                .glassThumb(
                    backdrop = backdrop,
                    shape = thumbShape,
                    tintBrush = TabGradient,
                    tintOpacity = 0.7f
                )
        )

        Row(modifier = Modifier.fillMaxSize()) {
            IconToggleButton(
                checked = selectedTab == EnergyDrinkSectionTab.SQUARED,
                onCheckedChange = { onTabSelected(EnergyDrinkSectionTab.SQUARED) },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_energy_drinks_list_view_squared),
                    contentDescription = "Squared",
                    tint = if (selectedTab == EnergyDrinkSectionTab.SQUARED) TextOnGradient else AccentWhite,
                    modifier = Modifier.size(24.dp)
                )
            }
            IconToggleButton(
                checked = selectedTab == EnergyDrinkSectionTab.LONGED,
                onCheckedChange = { onTabSelected(EnergyDrinkSectionTab.LONGED) },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_energy_drinks_list_view_longed),
                    contentDescription = "Longed",
                    tint = if (selectedTab == EnergyDrinkSectionTab.LONGED) TextOnGradient else AccentWhite,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}