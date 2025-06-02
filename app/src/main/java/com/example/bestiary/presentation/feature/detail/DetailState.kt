// presentation/feature/detail/DetailState.kt
package com.example.bestiary.presentation.feature.detail

import com.example.bestiary.domain.model.MonsterDetail

sealed class DetailState {
    object Loading : DetailState()
    data class Error(val message: String) : DetailState()
    data class Success(val monster: MonsterDetail) : DetailState()
}