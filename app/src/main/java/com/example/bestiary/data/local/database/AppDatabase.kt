package com.example.bestiary.data.local.database

import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bestiary.data.local.database.dao.MonsterDao
import com.example.bestiary.data.local.database.entity.MonsterEntity

@Database(
    entities = [MonsterEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun monsterDao(): MonsterDao
}