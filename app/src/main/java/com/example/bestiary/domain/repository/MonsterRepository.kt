package com.example.bestiary.domain.repository

// domain/repository/MonsterRepository.kt

import com.example.bestiary.domain.model.Monster
import com.example.bestiary.domain.model.MonsterDetail
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

interface MonsterRepository {
    suspend fun getAllMonsters(): Result<List<Monster>>
    suspend fun getMonsterDetail(index: String): Result<MonsterDetail>
    fun getFavoriteMonsters(): Flow<List<Monster>>
    suspend fun toggleFavorite(index: String): Result<Boolean>
}