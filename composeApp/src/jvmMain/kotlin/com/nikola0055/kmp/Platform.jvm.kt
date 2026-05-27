package com.nikola0055.kmp

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nikola0055.kmp.database.CatatanDb
import okio.Path.Companion.toPath
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun getDatabaseBuilder(): RoomDatabase.Builder<CatatanDb> {
    val dbFile = File(System.getProperty("user.home"), "catatan.db")
    return Room.databaseBuilder<CatatanDb>(
        name = dbFile.absolutePath
    )
}

actual fun formatDateTime(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    return formatter.format(Date())
}

actual fun createDataStore(): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            File(System.getProperty("user.home"), DATASTORE_FILE_NAME)
                .absolutePath
                .toPath()
        }
    )
}