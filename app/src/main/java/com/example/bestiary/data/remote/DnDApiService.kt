package com.example.bestiary.data.remote

import com.example.bestiary.domain.model.MonsterAction
import com.example.bestiary.domain.model.MonsterDetailResponse
import com.example.bestiary.domain.model.MonsterListResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface DnDApiService {
    @GET("api/monsters")
    suspend fun getAllMonsters(): MonsterListResponse

    @GET("api/monsters/{index}")
    suspend fun getMonsterByIndex(@Path("index") index: String): MonsterDetailResponse

    @GET("api/monsters/{index}/actions")
    suspend fun getMonsterActions(@Path("index") index: String): List<MonsterAction>

    @GET
    suspend fun getMonsterImage(@Url url: String): Response<ResponseBody> // Для загрузки изображений
}