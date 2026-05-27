package com.nikola0055.kmp

import android.os.Build
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nikola0055.kmp.database.CatatanDb
import okio.Path.Companion.toPath
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun getDatabaseBuilder(): RoomDatabase.Builder<CatatanDb> {
    val ctx = MainActivity.getAppContext()
    val dbFile = ctx.getDatabasePath("catatan.db")
    return Room.databaseBuilder<CatatanDb>(
        context = ctx,
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
            MainActivity.getAppContext()
                .filesDir
                .resolve(DATASTORE_FILE_NAME)
                .absolutePath
                .toPath()
        }
    )
}