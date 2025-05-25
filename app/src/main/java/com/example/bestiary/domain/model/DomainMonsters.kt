package com.example.bestiary.domain.model

data class Monster(
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

data class CustomMonster(
    val id: Int,
    val name: String,
    val imageUri: String?,
    // остальные поля...
)
