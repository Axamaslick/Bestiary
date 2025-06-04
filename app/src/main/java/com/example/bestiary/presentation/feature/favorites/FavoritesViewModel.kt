package com.example.bestiary.presentation.feature.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bestiary.domain.model.Monster
import com.example.bestiary.domain.usecase.GetFavoriteMonstersUseCase
import com.example.bestiary.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteMonstersUseCase: GetFavoriteMonstersUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _favoritesState = MutableStateFlow<FavoritesState>(FavoritesState.Loading)
    val favoritesState: StateFlow<FavoritesState> = _favoritesState.asStateFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        getFavoriteMonstersUseCase()
            .onEach { monsters ->
                _favoritesState.value = if (monsters.isEmpty()) {
                    FavoritesState.Empty
                } else {
                    FavoritesState.Success(monsters)
                }
            }
            .launchIn(viewModelScope)
    }

    fun toggleFavorite(index: String) {
        viewModelScope.launch {
            toggleFavoriteUseCase(index).onSuccess { isFavorite ->
                // Не нужно вызывать loadFavorites() - Flow автоматически обновится
                // так как мы используем Room с Flow
            }.onFailure { e ->
                // Обработка ошибки, если нужно
            }
        }
    }
}

sealed class FavoritesState {
    object Loading : FavoritesState()
    object Empty : FavoritesState()
    data class Success(val monsters: List<Monster>) : FavoritesState()
}