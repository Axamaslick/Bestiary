package com.example.bestiary.domain.usecase

import com.example.bestiary.domain.model.MonsterDetail
import com.example.bestiary.domain.repository.MonsterRepository
import javax.inject.Inject

class GetMonsterDetailUseCase @Inject constructor(
    private val repository: MonsterRepository
) {
    suspend operator fun invoke(index: String): Result<MonsterDetail> =
        repository.getMonsterDetail(index)
}