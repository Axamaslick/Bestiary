package com.example.bestiary.domain.usecase

class GetMonstersUseCase(
    private val repository: MonsterRepository
) {
    operator fun invoke(): Flow<List<Monster>> = repository.getMonsters()
}

class ToggleFavoriteUseCase(
    private val repository: MonsterRepository
) {
    suspend operator fun invoke(monsterId: String) = repository.toggleFavorite(monsterId)
}

class SaveCustomMonsterUseCase(
    private val repository: CustomMonsterRepository
) {
    suspend operator fun invoke(monster: CustomMonster) = repository.saveCustomMonster(monster)
}