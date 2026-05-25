package com.nikola0055.kmp

import androidx.room.Room
import androidx.room.RoomDatabase
import com.nikola0055.kmp.database.MahasiswaDb
import java.io.File

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun getDatabaseBuilder(): RoomDatabase.Builder<MahasiswaDb> {
    val dbFile = File(System.getProperty("user.home"), "mahasiswa.db")
    return Room.databaseBuilder<MahasiswaDb>(
        name = dbFile.absolutePath
    )
}