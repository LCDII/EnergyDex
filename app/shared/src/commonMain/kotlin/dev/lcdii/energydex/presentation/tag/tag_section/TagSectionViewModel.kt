package dev.lcdii.energydex.presentation.tag.tag_section

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.lcdii.energydex.core.presentation.toUiText
import dev.lcdii.energydex.domain.energydrink.usecase.ObserveEnergyDrinksForTagUseCase
import dev.lcdii.energydex.domain.energydrink.usecase.ObserveEnergyDrinkTagRelationsUseCase
import dev.lcdii.energydex.domain.tag.model.Tag
import dev.lcdii.energydex.domain.tag.model.TagWithEnergyDrinks
import dev.lcdii.energydex.domain.tag.usecase.ObserveTagsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TagSectionViewModel(
    private val observeTags: ObserveTagsUseCase,
    private val observeTagRelations: ObserveEnergyDrinkTagRelationsUseCase,
    private val observeEnergyDrinksForTag: ObserveEnergyDrinksForTagUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(TagSectionState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _state.value
    )

    init {
        startObservingTags()
    }

    fun onAction(action: TagSectionAction) {
        when (action) {
            is TagSectionAction.OnTagClick -> Unit
            TagSectionAction.OnCreateTagClick -> Unit
        }
    }

    private fun startObservingTags() = viewModelScope.launch {
        combine(observeTags(), observeTagRelations()) { tags, _ -> tags }
            .onStart { _state.update { it.copy(isLoading = true, errorMessage = null) } }
            .catch { error ->
                _state.update {
                    it.copy(isLoading = false, errorMessage = error.toUiText())
                }
            }
            .collect { tags ->
                loadTagCards(tags)
            }
    }

    private suspend fun loadTagCards(tags: List<Tag>) {
        val cards = mutableListOf<TagWithEnergyDrinks>()
        for (tag in tags) {
            val drinks = observeEnergyDrinksForTag(tag.id).first()
            cards += TagWithEnergyDrinks(tag = tag, drinks = drinks)
        }
        _state.update { it.copy(isLoading = false, tags = cards) }
    }
}
