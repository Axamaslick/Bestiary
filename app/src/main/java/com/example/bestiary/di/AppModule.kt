package com.example.bestiary.di

import android.content.Context
import androidx.room.Room
import com.example.bestiary.data.local.database.AppDatabase
import com.example.bestiary.data.local.database.dao.MonsterDao
import com.example.bestiary.data.remote.DnDApiService
import com.example.bestiary.data.repository.MonsterRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// di/AppModule.kt
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMonsterRepository(
        apiService: DnDApiService,
        monsterDao: MonsterDao
    ): MonsterRepositoryImpl = MonsterRepositoryImpl(apiService, monsterDao)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "bestiary.db"
        ).build()

    @Provides
    @Singleton
    fun provideMonsterDao(database: AppDatabase): MonsterDao = database.monsterDao()
}