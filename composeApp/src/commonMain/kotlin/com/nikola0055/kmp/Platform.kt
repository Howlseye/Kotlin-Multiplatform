package com.nikola0055.kmp

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun createDataStore(): DataStore<Preferences>
const val DATASTORE_FILE_NAME = "user_preferences.preferences_pb"
