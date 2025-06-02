package com.example.bestiary.data.remote.response

// data/remote/response/MonsterListResponse.kt
data class MonsterListResponse(
    val count: Int,
    val results: List<MonsterReference>
)

data class MonsterReference(
    val index: String,
    val name: String,
    val url: String
)

// data/remote/response/MonsterDetailResponse.kt
data class MonsterDetailResponse(
    val index: String,
    val name: String,
    val size: String,
    val type: String,
    val alignment: String,
    val armor_class: Int,
    val hit_points: Int,
    val hit_dice: String,
    val speed: Map<String, Int?>,
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,
    val proficiencies: List<Proficiency>,
    val damage_vulnerabilities: List<String>,
    val damage_resistances: List<String>,
    val damage_immunities: List<String>,
    val condition_immunities: List<ConditionImmunity>,
    val senses: Map<String, String?>,
    val languages: String,
    val challenge_rating: Float,
    val xp: Int,
    val special_abilities: List<SpecialAbility>?,
    val actions: List<MonsterAction>?,
    val legendary_actions: List<MonsterAction>?,
    val image: String?,
    val desc: String?
)

data class Proficiency(
    val value: Int,
    val proficiency: ProficiencyDetail
)

data class ProficiencyDetail(
    val index: String,
    val name: String,
    val url: String
)

data class ConditionImmunity(
    val index: String,
    val name: String,
    val url: String
)

data class SpecialAbility(
    val name: String,
    val desc: String
)

data class MonsterAction(
    val name: String,
    val desc: String,
    val attack_bonus: Int?,
    val damage: List<ActionDamage>?
)

data class ActionDamage(
    val damage_type: DamageType,
    val damage_dice: String
)

data class DamageType(
    val index: String,
    val name: String,
    val url: String
)