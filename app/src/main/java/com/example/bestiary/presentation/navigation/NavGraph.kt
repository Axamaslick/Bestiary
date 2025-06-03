package com.example.bestiary.presentation.navigation


import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.bestiary.R

fun NavController.navigateToSearch() {
    val options = navOptions {
        popUpTo(R.id.favoritesFragment) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
    navigate(R.id.searchFragment, null, options)
}

fun NavController.navigateToFavorites() {
    val options = navOptions {
        popUpTo(R.id.searchFragment) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
    navigate(R.id.favoritesFragment, null, options)
}