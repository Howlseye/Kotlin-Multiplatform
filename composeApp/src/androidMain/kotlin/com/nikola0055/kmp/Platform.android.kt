package com.nikola0055.kmp

import android.os.Build
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nikola0055.kmp.database.CatatanDb

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