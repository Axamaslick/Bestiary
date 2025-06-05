package com.example.bestiary.domain.model

import com.google.gson.annotations.SerializedName

data class Monster(
    val index: String,
    val name: String,
    val size: String,
    val type: String,
    @SerializedName("challenge_rating")
    val challengeRating: Float,
    val isFavorite: Boolean = false,
    val imageUrl: String? = null
)