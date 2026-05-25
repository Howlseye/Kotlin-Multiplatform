package com.nikola0055.kmp

import androidx.room.Room
import androidx.room.RoomDatabase
import com.nikola0055.kmp.database.CatatanDb
import java.io.File

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