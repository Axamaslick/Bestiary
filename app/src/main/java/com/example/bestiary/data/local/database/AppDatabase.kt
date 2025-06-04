package com.example.bestiary.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.bestiary.data.local.database.dao.MonsterDao
import com.example.bestiary.data.local.database.entity.MonsterEntity

@Database(
    entities = [MonsterEntity::class],
    version = 3, // Увеличиваем до 3
    exportSchema = true // Включаем для отладки
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun monsterDao(): MonsterDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Удаляем старую таблицу кастомных монстров
                database.execSQL("DROP TABLE IF EXISTS custom_monsters")
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Пересоздаем таблицу monsters с правильной структурой
                database.execSQL("""
                    CREATE TABLE new_monsters (
                        `index` TEXT NOT NULL PRIMARY KEY,
                        name TEXT NOT NULL,
                        size TEXT NOT NULL,
                        type TEXT NOT NULL,
                        alignment TEXT NOT NULL,
                        armorClass INTEGER NOT NULL,
                        hitPoints INTEGER NOT NULL,
                        hitDice TEXT NOT NULL,
                        strength INTEGER NOT NULL,
                        dexterity INTEGER NOT NULL,
                        constitution INTEGER NOT NULL,
                        intelligence INTEGER NOT NULL,
                        wisdom INTEGER NOT NULL,
                        charisma INTEGER NOT NULL,
                        challengeRating REAL NOT NULL,
                        xp INTEGER NOT NULL,
                        imageUrl TEXT,
                        description TEXT,
                        isFavorite INTEGER NOT NULL DEFAULT 0
                    )
                """.trimIndent())

                // Копируем данные из старой таблицы
                database.execSQL("""
                    INSERT INTO new_monsters SELECT * FROM monsters
                """.trimIndent())

                // Удаляем старую таблицу
                database.execSQL("DROP TABLE monsters")

                // Переименовываем новую таблицу
                database.execSQL("ALTER TABLE new_monsters RENAME TO monsters")

                // Создаем индекс
                database.execSQL("CREATE INDEX index_monsters_name ON monsters(name)")
            }
        }
    }
}