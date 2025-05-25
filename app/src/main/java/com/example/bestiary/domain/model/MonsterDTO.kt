package com.example.bestiary.domain.model

// Модель для списка монстров
data class MonsterListResponse(
    val count: Int,
    val results: List<MonsterReference>
)

data class MonsterReference(
    val index: String,
    val name: String,
    val url: String
)

// Модель для деталей монстра
data class MonsterDetailResponse(
    val index: String,
    val name: String,
    val size: String,
    val type: String,
    val alignment: String,
    val armor_class: Int,
    val hit_points: Int,
    val hit_dice: String,
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,
    val actions: List<MonsterAction>,
    val special_abilities: List<MonsterSpecialAbility>?,
    val image: String? // Некоторые монстры могут иметь изображения
)

data class MonsterAction(
    val name: String,
    val desc: String,
    val attack_bonus: Int?,
    val damage: List<MonsterDamage>?
)

data class MonsterDamage(
    val damage_type: MonsterDamageType,
    val damage_dice: String
)

data class MonsterDamageType(
    val index: String,
    val name: String
)

data class MonsterSpecialAbility(
    val name: String,
    val desc: String
)