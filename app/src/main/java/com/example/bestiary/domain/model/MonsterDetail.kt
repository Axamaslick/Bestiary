package com.example.bestiary.domain.model

data class MonsterDetail(
    val index: String,
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
    val specialAbilities: List<SpecialAbility>?,
    val actions: List<MonsterAction>?,
    val imageUrl: String?,
    val description: String?,
    val isFavorite: Boolean = false
)

data class SpecialAbility(
    val name: String,
    val desc: String
)

data class MonsterAction(
    val name: String,
    val desc: String,
    val attackBonus: Int?,
    val damage: List<ActionDamage>?
)

data class ActionDamage(
    val damageType: String,
    val damageDice: String
)