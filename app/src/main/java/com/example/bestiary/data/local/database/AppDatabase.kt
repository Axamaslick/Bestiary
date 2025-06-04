package com.example.bestiary.data.local.database

import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bestiary.data.local.CustomMonsterEntity
import com.example.bestiary.data.local.database.dao.MonsterDao
import com.example.bestiary.data.local.database.entity.MonsterEntity

@Database(
    entities = [MonsterEntity::class, CustomMonsterEntity::class], // Добавьте CustomMonsterEntity в entities
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun monsterDao(): MonsterDao
}
