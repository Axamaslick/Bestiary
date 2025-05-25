package com.example.bestiary.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface DnDApiService {
    @GET("api/monsters")
    suspend fun getAllMonsters(): List<MonsterDto>

    @GET("api/monsters/{index}")
    suspend fun getMonster(@Path("index") index: String): MonsterDto
}