package com.example.bestiary

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Удаляем таблицу кастомных монстров, если она существует
        database.execSQL("DROP TABLE IF EXISTS custom_monsters")
    }
}