package com.nikola0055.kmp.navigation

sealed class Screen(val route: String) {
    object Home : Screen("mainScreen")
    object About : Screen(("aboutScreen"))
}