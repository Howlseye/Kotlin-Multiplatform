package com.nikola0055.kmp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.nikola0055.kmp.ui.screen.MainScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    val darkTheme = isSystemInDarkTheme()

    MaterialTheme(
        colorScheme = if (darkTheme) darkColorScheme() else lightColorScheme()
    ) {
        MainScreen()
    }
}
