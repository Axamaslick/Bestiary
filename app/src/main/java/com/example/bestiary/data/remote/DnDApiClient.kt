package com.example.bestiary.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object DnDApiClient {
    private const val BASE_URL = "https://www.dnd5eapi.co/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(OkHttpClient.Builder().build())
        .build()

    val apiService: DnDApiService = retrofit.create(DnDApiService::class.java)
}