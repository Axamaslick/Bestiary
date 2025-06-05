package com.example.bestiary.domain.model

import com.google.gson.annotations.SerializedName

data class MonsterDetail(
    val index: String,
    val name: String,
    val size: String,
    val type: String,
    val alignment: String,
    @SerializedName("armor_class")
    val armorClass: Int,
    @SerializedName("hit_points")
    val hitPoints: Int,
    @SerializedName("hit_dice")
    val hitDice: String,
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,
    @SerializedName("challenge_rating")
    val challengeRating: Float,
    val xp: Int,
    @SerializedName("special_abilities")
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
    @SerializedName("attack_bonus")
    val attackBonus: Int?,
    val damage: List<ActionDamage>?
)

data class ActionDamage(
    @SerializedName("damage_type")
    val damageType: String,
    @SerializedName("damage_dice")
    val damageDice: String
)