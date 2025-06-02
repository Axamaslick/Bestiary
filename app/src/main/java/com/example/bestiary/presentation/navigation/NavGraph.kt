package com.example.bestiary.presentation.navigation

import androidx.navigation.NavController

fun NavController.navigateToSearch() {
    navigate(R.id.searchFragment) {
        popUpTo(R.id.favoritesFragment) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateToFavorites() {
    navigate(R.id.favoritesFragment) {
        popUpTo(R.id.searchFragment) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}