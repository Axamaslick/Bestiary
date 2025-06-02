package com.example.bestiary.data.repository

import com.example.bestiary.data.remote.DnDApiService
import com.example.bestiary.domain.model.Monster
import com.example.bestiary.domain.model.MonsterDetailResponse
import com.example.bestiary.domain.model.MonsterReference
import javax.inject.Inject

class MonsterRepository @Inject constructor(
    private val apiService: DnDApiService
) {
    suspend fun getAllMonsters(): List<Monster> {
        val response = apiService.getAllMonsters()
        return response.results.map { it.toDomain() }
    }

    suspend fun getMonsterDetails(index: String): MonsterDetails {
        val response = apiService.getMonsterByIndex(index)
        return response.toDomain()
    }

    private fun MonsterReference.toDomain(): Monster {
        return Monster(
            id = index,
            name = name,
            imageUrl = null, // Будет загружено отдельно
            detailsUrl = url
        )
    }

    private fun MonsterDetailResponse.toDomain(): MonsterDetails {
        return MonsterDetails(
            id = index,
            name = name,
            size = size,
            type = type,
            alignment = alignment,
            armorClass = armor_class,
            hitPoints = hit_points,
            hitDice = hit_dice,
            strength = strength,
            dexterity = dexterity,
            constitution = constitution,
            intelligence = intelligence,
            wisdom = wisdom,
            charisma = charisma,
            actions = actions.map { it.toDomain() },
            specialAbilities = special_abilities?.map { it.toDomain() } ?: emptyList(),
            imageUrl = image
        )
    }
}