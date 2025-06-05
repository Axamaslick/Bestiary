// data/remote/response/MonsterExtensions.kt
package com.example.bestiary.data.remote.response

import com.example.bestiary.data.local.database.entity.MonsterEntity
import com.example.bestiary.domain.model.Monster
import com.example.bestiary.domain.model.MonsterDetail
import com.example.bestiary.domain.model.SpecialAbility
import com.example.bestiary.domain.model.MonsterAction
import com.example.bestiary.domain.model.ActionDamage

fun MonsterReference.toMonster(isFavorite: Boolean = false): Monster = Monster(
    index = index,
    name = name,
    size = "", // Эти поля будут заполнены в детальной информации
    type = "",
    challengeRating = 0f,
    isFavorite = isFavorite,
    imageUrl = null
)

fun MonsterDetailResponse.toMonsterDetail(isFavorite: Boolean = false): MonsterDetail = MonsterDetail(
    index = index,
    name = name,
    size = size,
    type = type,
    alignment = alignment,
    armorClass = armorClass.firstOrNull()?.value ?: 0,  // Используем camelCase и берём значение из списка
    hitPoints = hitPoints,
    hitDice = hitDice,
    strength = strength,
    dexterity = dexterity,
    constitution = constitution,
    intelligence = intelligence,
    wisdom = wisdom,
    charisma = charisma,
    challengeRating = challengeRating,
    xp = xp ?: 0,
    specialAbilities = specialAbilities?.map {
        SpecialAbility(it.name, it.desc)
    },
    actions = actions?.map {
        MonsterAction(
            name = it.name,
            desc = it.desc,
            attackBonus = it.attackBonus,
            damage = it.damage?.map { damage ->
                ActionDamage(
                    damageType = damage.damageType.name,
                    damageDice = damage.damageDice
                )
            }
        )
    },
    imageUrl = image?.let { "https://www.dnd5eapi.co$it" },  // Формируем полный URL
    description = desc,
    isFavorite = isFavorite
)


fun MonsterDetail.toMonsterEntity(): MonsterEntity = MonsterEntity(
    index = index,
    name = name,
    size = size,
    type = type,
    alignment = alignment,
    armorClass = armorClass,
    hitPoints = hitPoints,
    hitDice = hitDice,
    strength = strength,
    dexterity = dexterity,
    constitution = constitution,
    intelligence = intelligence,
    wisdom = wisdom,
    charisma = charisma,
    challengeRating = challengeRating,
    xp = xp,
    imageUrl = imageUrl,
    description = description,
    isFavorite = isFavorite
)

fun MonsterEntity.toMonster(): Monster = Monster(
    index = index,
    name = name,
    size = size,
    type = type,
    challengeRating = challengeRating,
    isFavorite = isFavorite,
    imageUrl = imageUrl
)

fun MonsterEntity.toMonsterDetail(): MonsterDetail = MonsterDetail(
    index = index,
    name = name,
    size = size,
    type = type,
    alignment = alignment,
    armorClass = armorClass,
    hitPoints = hitPoints,
    hitDice = hitDice,
    strength = strength,
    dexterity = dexterity,
    constitution = constitution,
    intelligence = intelligence,
    wisdom = wisdom,
    charisma = charisma,
    challengeRating = challengeRating,
    xp = xp,
    specialAbilities = null, // Эти данные не хранятся в БД
    actions = null, // Эти данные не хранятся в БД
    imageUrl = imageUrl,
    description = description,
    isFavorite = isFavorite
)