// data/repository/MonsterRepositoryImpl.kt
package com.example.bestiary.data.repository

import android.util.Log
import com.example.bestiary.data.local.database.dao.MonsterDao
import com.example.bestiary.data.remote.DnDApiService
import com.example.bestiary.data.remote.response.MonsterDetailResponse
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

    private var cachedMonsters: List<Monster>? = null

    private companion object {
        const val TAG = "MonsterRepository"
    }

    override suspend fun getAllMonsters(): Result<List<Monster>> {
        cachedMonsters?.let {
            return Result.success(it)
        }
        return try {
            Log.d(TAG, "Fetching all monsters from API")
            val response = apiService.getAllMonsters()

            if (!response.isSuccessful) {
                Log.e(TAG, "API request failed: ${response.code()}")
                return Result.failure(Exception("API request failed"))
            }

            val remoteMonsters = response.body()?.results ?: emptyList()

            // Сохраняем всех монстров в БД
            val entities = remoteMonsters.map { it.toMonsterEntity() }
            monsterDao.insertMonsters(entities)

            val localMonsters = monsterDao.getAllMonsters()
            val monsters = localMonsters.map { it.toMonster() }
            cachedMonsters = monsters // Сохраняем в кэш ДО возврата результата
            Result.success(monsters)
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching monsters", e)
            Result.failure(e)
        }
    }

    override suspend fun getMonsterDetail(index: String): Result<MonsterDetail> {
        return try {
            Log.d(TAG, "Fetching monster details for index: $index")

            // Сначала пробуем получить из локальной базы
            val localMonster = monsterDao.getMonsterByIndex(index)
            if (localMonster != null && localMonster.hitPoints != 0) {
                Log.d(TAG, "Found monster in local database with data")
                return Result.success(localMonster.toMonsterDetail())
            }

            // Если нет данных или данные пустые — делаем запрос к API
            val response = apiService.getMonsterByIndex(index)
            if (!response.isSuccessful) {
                Log.e(TAG, "API request failed: ${response.code()}")
                return Result.failure(Exception("Monster not found"))
            }

            val monsterDetailResponse = response.body()
                ?: return Result.failure(Exception("Invalid response format"))

            val monsterDetail = monsterDetailResponse.toMonsterDetail()

            // Сохраняем в базу
            monsterDao.insertMonster(monsterDetail.toMonsterEntity())
            Log.d(TAG, "Saved monster to local database after API fetch")

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

            // Получаем текущее состояние из БД
            val monster = monsterDao.getMonsterByIndex(index)
                ?: return Result.failure(Exception("Monster not found"))

            // Инвертируем статус
            val newFavoriteStatus = !monster.isFavorite
            val updatedMonster = monster.copy(isFavorite = newFavoriteStatus)

            // Обновляем в БД
            monsterDao.updateMonster(updatedMonster)

            Log.d(TAG, "Favorite status toggled to $newFavoriteStatus")
            Result.success(newFavoriteStatus)
        } catch (e: Exception) {
            Log.e(TAG, "Error toggling favorite", e)
            Result.failure(e)
        }
    }

    override fun searchMonsters(query: String): Flow<List<Monster>> {
        return monsterDao.searchMonsters(query)
            .map { entities ->
                Log.d("SEARCH_DEBUG", "Found ${entities.size} entities for query: $query")
                entities.map { it.toMonster() }
            }
    }
}
