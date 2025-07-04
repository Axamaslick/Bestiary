package com.example.bestiary.domain.usecase

import com.example.bestiary.domain.model.Monster
import com.example.bestiary.domain.repository.MonsterRepository
import javax.inject.Inject

class GetAllMonstersUseCase @Inject constructor(
    private val repository: MonsterRepository
) {
    suspend operator fun invoke(): Result<List<Monster>> = repository.getAllMonsters()
}
