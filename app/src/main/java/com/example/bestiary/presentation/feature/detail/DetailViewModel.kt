// presentation/feature/detail/DetailViewModel.kt
package com.example.bestiary.presentation.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bestiary.domain.repository.MonsterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MonsterRepository
) : ViewModel() {

    private val _monsterDetailState = MutableStateFlow<DetailState>(DetailState.Loading)
    val monsterDetailState: StateFlow<DetailState> = _monsterDetailState.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    fun loadMonsterDetail(index: String) {
        viewModelScope.launch {
            _monsterDetailState.value = DetailState.Loading
            repository.getMonsterDetail(index)
                .onSuccess { monster ->
                    _monsterDetailState.value = DetailState.Success(monster)
                    _isFavorite.value = monster.isFavorite
                }
                .onFailure {
                    _monsterDetailState.value = DetailState.Error(it.message ?: "Unknown error")
                }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val currentIndex = (_monsterDetailState.value as? DetailState.Success)?.monster?.index
            currentIndex?.let { index ->
                repository.toggleFavorite(index).onSuccess {
                    _isFavorite.value = it
                }
            }
        }
    }
}