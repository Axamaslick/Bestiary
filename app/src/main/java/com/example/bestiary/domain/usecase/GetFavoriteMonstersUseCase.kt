package com.example.bestiary.domain.usecase

import com.example.bestiary.domain.model.Monster
import com.example.bestiary.domain.repository.MonsterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMonstersUseCase @Inject constructor(
    private val repository: MonsterRepository
) {
    operator fun invoke(): Flow<List<Monster>> = repository.getFavoriteMonsters()
}