package com.example.bestiary.data.repository

import com.example.bestiary.data.local.database.dao.MonsterDao
import com.example.bestiary.data.remote.DnDApiService
import com.example.bestiary.domain.model.Monster
import com.example.bestiary.domain.repository.MonsterRepository
import javax.inject.Inject

// data/repository/MonsterRepositoryImpl.kt
class MonsterRepositoryImpl @Inject constructor(
    private val apiService: DnDApiService,
    private val monsterDao: MonsterDao
) : MonsterRepository {

    override suspend fun getAllMonsters(): Result<List<Monster>> {
        return try {
            val response = apiService.getAllMonsters()
            if (response.isSuccessful) {
                Result.success(response.body()?.results?.map { it.toMonster() } ?: emptyList())
            } else {
                Result.failure(Exception("Failed to fetch monsters"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMonsterDetail(index: String): Result<MonsterDetail> {
        return try {
            val localMonster = monsterDao.getMonsterByIndex(index)
            if (localMonster != null) {
                return Result.success(localMonster.toMonsterDetail())
            }

            val response = apiService.getMonsterByIndex(index)
            if (response.isSuccessful) {
                val monsterDetail = response.body()?.toMonsterDetail()
                if (monsterDetail != null) {
                    monsterDao.insertMonster(monsterDetail.toMonsterEntity())
                }
                Result.success(monsterDetail ?: throw Exception("Monster not found"))
            } else {
                Result.failure(Exception("Failed to fetch monster details"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getFavoriteMonsters(): Flow<List<Monster>> {
        return monsterDao.getFavoriteMonsters().map { list ->
            list.map { it.toMonster() }
        }
    }

    override suspend fun toggleFavorite(index: String): Result<Boolean> {
        return try {
            val monster = monsterDao.getMonsterByIndex(index)
            if (monster != null) {
                val updatedMonster = monster.copy(isFavorite = !monster.isFavorite)
                monsterDao.updateMonster(updatedMonster)
                Result.success(updatedMonster.isFavorite)
            } else {
                Result.failure(Exception("Monster not found in local database"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}