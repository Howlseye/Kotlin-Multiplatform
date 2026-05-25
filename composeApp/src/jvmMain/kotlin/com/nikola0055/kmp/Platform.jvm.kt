package com.nikola0055.kmp

import androidx.room.Room
import androidx.room.RoomDatabase
import com.nikola0055.kmp.database.CatatanDb
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