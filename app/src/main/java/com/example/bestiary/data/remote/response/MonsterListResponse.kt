package com.example.bestiary.data.remote.response

import com.example.bestiary.data.local.database.entity.MonsterEntity

// data/remote/response/MonsterListResponse.kt
data class MonsterListResponse(
    val count: Int,
    val results: List<MonsterReference>
)

data class MonsterReference(
    val index: String,
    val name: String,
    val url: String
) {
    fun toMonsterEntity(): MonsterEntity = MonsterEntity(
        index = index,
        name = name,
        size = "", // Будет обновлено при детальной загрузке
        type = "",
        alignment = "",
        armorClass = 0,
        hitPoints = 0,
        hitDice = "",
        strength = 0,
        dexterity = 0,
        constitution = 0,
        intelligence = 0,
        wisdom = 0,
        charisma = 0,
        challengeRating = 0f,
        xp = 0,
        imageUrl = null,
        description = null,
        isFavorite = false
    )
}