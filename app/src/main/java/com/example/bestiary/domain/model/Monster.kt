package com.example.bestiary.domain.model

data class Monster(
    val index: String,
    val name: String,
    val size: String,
    val type: String,
    val challengeRating: Float,
    val isFavorite: Boolean = false,
    val imageUrl: String? = null
)