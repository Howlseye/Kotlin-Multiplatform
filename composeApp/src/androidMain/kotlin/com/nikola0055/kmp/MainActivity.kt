package com.nikola0055.kmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this

        setContent {
            App()
        }
    }

    companion object {
        private var instance: MainActivity? = null

        fun getAppContext(): MainActivity {
            return instance ?: throw IllegalStateException("MainActivity not initialized")
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}