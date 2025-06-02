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