package com.nikola0055.kmp

import android.os.Build
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nikola0055.kmp.database.MahasiswaDb

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun getDatabaseBuilder(): RoomDatabase.Builder<MahasiswaDb> {
    val ctx = MainActivity.getAppContext()
    val dbFile = ctx.getDatabasePath("mahasiswa.db")
    return Room.databaseBuilder<MahasiswaDb>(
        context = ctx,
        name = dbFile.absolutePath
    )
}