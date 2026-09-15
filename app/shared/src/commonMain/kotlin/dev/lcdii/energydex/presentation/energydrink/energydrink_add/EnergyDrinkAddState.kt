package dev.lcdii.energydex.presentation.energydrink.energydrink_add

import dev.lcdii.energydex.core.presentation.UiText
import dev.lcdii.energydex.domain.tag.model.Tag

data class EnergyDrinkAddState(
    val name: String = "",
    val isNameTextValid: Boolean = true,
    val description: String = "",
    val imagePath: String? = null,
    val ratingText: String = "",
    val isRatingTextValid: Boolean = true,
    val rating: Double? = null,
    val amount: Int = 1,
    val isSaved: Boolean = false,
    val isSaving: Boolean = false,
    val errorMessage: UiText? = null,
    val availableTags: List<Tag> = emptyList(),
    val selectedTagIds: Set<Long> = emptySet()
)
