package com.example.bestiary.domain.usecase

import com.example.bestiary.domain.model.Monster
import com.example.bestiary.domain.model.MonsterDetail
import com.example.bestiary.domain.repository.MonsterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// domain/usecase/GetAllMonstersUseCase.kt
class GetAllMonstersUseCase @Inject constructor(
    private val repository: MonsterRepository
) {
    suspend operator fun invoke(): Result<List<Monster>> = repository.getAllMonsters()
}

// domain/usecase/GetMonsterDetailUseCase.kt
class GetMonsterDetailUseCase @Inject constructor(
    private val repository: MonsterRepository
) {
    suspend operator fun invoke(index: String): Result<MonsterDetail> =
        repository.getMonsterDetail(index)
}

// domain/usecase/GetFavoriteMonstersUseCase.kt
class GetFavoriteMonstersUseCase @Inject constructor(
    private val repository: MonsterRepository
) {
    operator fun invoke(): Flow<List<Monster>> = repository.getFavoriteMonsters()
}

// domain/usecase/ToggleFavoriteUseCase.kt
class ToggleFavoriteUseCase @Inject constructor(
    private val repository: MonsterRepository
) {
    suspend operator fun invoke(index: String): Result<Boolean> =
        repository.toggleFavorite(index)
}