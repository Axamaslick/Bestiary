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
    armorClass = armor_class,
    hitPoints = hit_points,
    hitDice = hit_dice,
    strength = strength,
    dexterity = dexterity,
    constitution = constitution,
    intelligence = intelligence,
    wisdom = wisdom,
    charisma = charisma,
    challengeRating = challenge_rating,
    xp = xp,
    specialAbilities = special_abilities?.map {
        SpecialAbility(it.name, it.desc)
    },
    actions = actions?.map {
        MonsterAction(
            name = it.name,
            desc = it.desc,
            attackBonus = it.attack_bonus,
            damage = it.damage?.map { damage ->
                ActionDamage(
                    damageType = damage.damage_type.name,
                    damageDice = damage.damage_dice
                )
            }
        )
    },
    imageUrl = image,
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