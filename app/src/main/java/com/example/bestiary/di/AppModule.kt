// di/AppModule.kt
package com.example.bestiary.di

import android.content.Context
import androidx.room.Room
import com.example.bestiary.data.local.database.AppDatabase
import com.example.bestiary.data.local.database.dao.MonsterDao
import com.example.bestiary.data.remote.DnDApiService
import com.example.bestiary.data.repository.MonsterRepositoryImpl
import com.example.bestiary.domain.repository.MonsterRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMonsterRepository(
        repositoryImpl: MonsterRepositoryImpl
    ): MonsterRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "bestiary.db"
        ).build()
    }

    @Provides
    fun provideMonsterDao(database: AppDatabase): MonsterDao = database.monsterDao()
}
