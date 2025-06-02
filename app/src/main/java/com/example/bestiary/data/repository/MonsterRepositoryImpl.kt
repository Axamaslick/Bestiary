// data/repository/MonsterRepositoryImpl.kt
package com.example.bestiary.data.repository

import com.example.bestiary.data.local.database.dao.MonsterDao
import com.example.bestiary.data.remote.DnDApiService
import com.example.bestiary.data.remote.response.toMonster
import com.example.bestiary.data.remote.response.toMonsterDetail
import com.example.bestiary.data.remote.response.toMonsterEntity
import com.example.bestiary.domain.model.Monster
import com.example.bestiary.domain.model.MonsterDetail
import com.example.bestiary.domain.repository.MonsterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MonsterRepositoryImpl @Inject constructor(
    private val apiService: DnDApiService,
    private val monsterDao: MonsterDao
) : MonsterRepository {

    override suspend fun getAllMonsters(): Result<List<Monster>> {
        return try {
            val response = apiService.getAllMonsters()
            if (response.isSuccessful) {
                val localMonsters = monsterDao.getAllMonsters()
                val localMonstersMap = localMonsters.associateBy { it.index }

                val monsters = response.body()?.results?.map { remoteMonster ->
                    val localMonster = localMonstersMap[remoteMonster.index]
                    remoteMonster.toMonster(localMonster?.isFavorite ?: false)
                } ?: emptyList()

                Result.success(monsters)
            } else {
                Result.failure(Exception("Failed to fetch monsters: ${response.code()}"))
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
                Result.failure(Exception("Failed to fetch monster details: ${response.code()}"))
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
            var monster = monsterDao.getMonsterByIndex(index)

            if (monster == null) {
                // Если монстра нет в БД, сначала загружаем его
                val response = getMonsterDetail(index)
                if (response.isFailure) {
                    return Result.failure(response.exceptionOrNull()!!)
                }
                monster = monsterDao.getMonsterByIndex(index)!!
            }

            val updatedMonster = monster.copy(isFavorite = !monster.isFavorite)
            monsterDao.updateMonster(updatedMonster)
            Result.success(updatedMonster.isFavorite)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}