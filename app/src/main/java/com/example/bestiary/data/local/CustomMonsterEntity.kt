package com.example.bestiary.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_monsters")
data class CustomMonsterEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val imageUri: String?,
    val level: Int,
    val hp: Int,
    val mana: Int,
    val damage: Int,
    val armor: Int,
    val strength: Int,
    val constitution: Int,
    val dexterity: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,
    val specialAttacks: String, // JSON список SpecialAttack
    val description: String,
    val isFavorite: Boolean = false
)