// data/repository/MonsterRepositoryImpl.kt
package com.example.bestiary.data.repository

import android.util.Log
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

    private companion object {
        const val TAG = "MonsterRepository"
    }

    override suspend fun getAllMonsters(): Result<List<Monster>> {
        return try {
            Log.d(TAG, "Fetching all monsters from API")
            val response = apiService.getAllMonsters()

            if (!response.isSuccessful) {
                Log.e(TAG, "API request failed: ${response.code()}")
                return Result.failure(Exception("API request failed"))
            }

            val remoteMonsters = response.body()?.results ?: emptyList()
            val localMonsters = monsterDao.getAllMonsters()
            val localMonstersMap = localMonsters.associateBy { it.index }

            val monsters = remoteMonsters.map { remoteMonster ->
                remoteMonster.toMonster(
                    isFavorite = localMonstersMap[remoteMonster.index]?.isFavorite ?: false
                )
            }

            Log.d(TAG, "Successfully fetched ${monsters.size} monsters")
            Result.success(monsters)
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching monsters", e)
            Result.failure(e)
        }
    }

    override suspend fun getMonsterDetail(index: String): Result<MonsterDetail> {
        return try {
            Log.d(TAG, "Fetching monster details for index: $index")

            // Сначала проверяем локальную БД
            monsterDao.getMonsterByIndex(index)?.let { localMonster ->
                Log.d(TAG, "Found monster in local database")
                return Result.success(localMonster.toMonsterDetail())
            }

            // Если нет в БД, запрашиваем с API
            val response = apiService.getMonsterByIndex(index)
            if (!response.isSuccessful) {
                Log.e(TAG, "API request failed: ${response.code()}")
                return Result.failure(Exception("Monster not found"))
            }

            val monsterDetail = response.body()?.toMonsterDetail()
                ?: return Result.failure(Exception("Invalid response format"))

            // Сохраняем в БД для будущих запросов
            monsterDao.insertMonster(monsterDetail.toMonsterEntity())
            Log.d(TAG, "Saved monster to local database")

            Result.success(monsterDetail)
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching monster details", e)
            Result.failure(e)
        }
    }

    override fun getFavoriteMonsters(): Flow<List<Monster>> {
        return monsterDao.getFavoriteMonsters()
            .map { favorites ->
                favorites.map { it.toMonster() }
            }
    }

    override suspend fun toggleFavorite(index: String): Result<Boolean> {
        return try {
            Log.d(TAG, "Toggling favorite for monster: $index")

            var monster = monsterDao.getMonsterByIndex(index)

            if (monster == null) {
                // Если монстра нет в БД, сначала загружаем его
                Log.d(TAG, "Monster not in DB, fetching from API")
                val response = getMonsterDetail(index)

                if (response.isFailure) {
                    return Result.failure(response.exceptionOrNull()!!)
                }

                monster = monsterDao.getMonsterByIndex(index)
                    ?: return Result.failure(Exception("Failed to save monster to DB"))
            }

            val updatedMonster = monster.copy(isFavorite = !monster.isFavorite)
            monsterDao.updateMonster(updatedMonster)

            Log.d(TAG, "Favorite status toggled to ${updatedMonster.isFavorite}")
            Result.success(updatedMonster.isFavorite)
        } catch (e: Exception) {
            Log.e(TAG, "Error toggling favorite", e)
            Result.failure(e)
        }
    }

    override fun searchMonsters(query: String): Flow<List<Monster>> {
        return monsterDao.searchMonsters(query)
            .map { entities -> entities.map { it.toMonster() } }
    }
}