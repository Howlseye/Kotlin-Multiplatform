package com.nikola0055.kmp

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.RoomDatabase
import com.nikola0055.kmp.database.CatatanDb

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun getDatabaseBuilder(): RoomDatabase.Builder<CatatanDb>

expect fun formatDateTime(): String

expect fun createDataStore(): DataStore<Preferences>
const val DATASTORE_FILE_NAME = "settings_preference.preferences_pb"
