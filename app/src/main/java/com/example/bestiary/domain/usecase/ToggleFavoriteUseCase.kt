package com.example.bestiary.domain.usecase

import com.example.bestiary.domain.repository.MonsterRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: MonsterRepository
) {
    suspend operator fun invoke(index: String): Result<Boolean> =
        repository.toggleFavorite(index)
}