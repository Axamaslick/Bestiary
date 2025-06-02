package com.example.bestiary.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monsters")
data class MonsterEntity(
    @PrimaryKey val index: String,
    val name: String,
    val size: String,
    val type: String,
    val alignment: String,
    val armorClass: Int,
    val hitPoints: Int,
    val hitDice: String,
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,
    val challengeRating: Float,
    val xp: Int,
    val imageUrl: String?,
    val description: String?,
    val isFavorite: Boolean = false
)