package com.example.bestiary.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bestiary.data.local.database.entity.MonsterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MonsterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonster(monster: MonsterEntity)

    @Delete
    suspend fun deleteMonster(monster: MonsterEntity)

    @Query("SELECT * FROM monsters WHERE isFavorite = 1")
    fun getFavoriteMonsters(): Flow<List<MonsterEntity>>

    @Query("SELECT * FROM monsters WHERE `index` = :index") // Обратные кавычки вокруг index
    suspend fun getMonsterByIndex(index: String): MonsterEntity?

    @Update
    suspend fun updateMonster(monster: MonsterEntity)

    @Query("SELECT * FROM monsters")
    suspend fun getAllMonsters(): List<MonsterEntity>
}