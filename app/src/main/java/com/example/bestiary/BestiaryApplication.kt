package com.example.bestiary

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BestiaryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Инициализация (если требуется)
    }
}