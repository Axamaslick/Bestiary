package com.example.bestiary.data.remote

import com.example.bestiary.data.remote.response.MonsterDetailResponse
import com.example.bestiary.data.remote.response.MonsterListResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface DnDApiService {
    @GET("api/2014/monsters/")
    suspend fun getAllMonsters(): Response<MonsterListResponse>

    @GET("api/2014/monsters/{index}/")
    suspend fun getMonsterByIndex(@Path("index") index: String): Response<MonsterDetailResponse>

    @GET
    suspend fun getMonsterImage(@Url url: String): Response<ResponseBody>
}