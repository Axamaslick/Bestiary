// data/remote/response/MonsterDetailResponse.kt
package com.example.bestiary.data.remote.response

import com.squareup.moshi.Json

data class ArmorClassResponse(
    val type: String?,
    val value: Int
)

data class MonsterDetailResponse(
    val index: String,
    val name: String,
    val size: String,
    val type: String,
    val alignment: String,
    @Json(name = "armor_class")
    val armorClass: List<ArmorClassResponse>,
    @Json(name = "hit_points")
    val hitPoints: Int,
    @Json(name = "hit_dice")
    val hitDice: String,
    val speed: Map<String, String?>?, // Изменено на String?
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,
    val proficiencies: List<Proficiency>,
    val damage_vulnerabilities: List<String>?,
    val damage_resistances: List<String>?,
    val damage_immunities: List<String>?,
    val condition_immunities: List<ConditionImmunity>?,
    val senses: Map<String, String?>?, // Изменено на String?
    val languages: String?, // Может быть null
    @Json(name = "challenge_rating")
    val challengeRating: Float,
    val xp: Int?, // Может быть null
    @Json(name = "special_abilities")
    val specialAbilities: List<SpecialAbility>?,
    val actions: List<MonsterAction>?,
    @Json(name = "legendary_actions")
    val legendaryActions: List<MonsterAction>?,
    @Json(name = "image")
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
    @Json(name = "attack_bonus")
    val attackBonus: Int?,
    val damage: List<ActionDamage>?
)

data class ActionDamage(
    @Json(name = "damage_type")
    val damageType: DamageType,
    @Json(name = "damage_dice")
    val damageDice: String
)

data class DamageType(
    val index: String,
    val name: String,
    val url: String
)

