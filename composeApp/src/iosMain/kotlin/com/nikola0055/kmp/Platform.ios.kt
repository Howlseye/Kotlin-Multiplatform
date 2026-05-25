package com.nikola0055.kmp

import androidx.room.Room
import androidx.room.RoomDatabase
import com.nikola0055.kmp.database.MahasiswaDb
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun getDatabaseBuilder(): RoomDatabase.Builder<MahasiswaDb> {
    val documentDirectory = NSSearchPathForDirectoriesInDomains(
        NSDocumentDirectory,
        NSUserDomainMask,
        true
    ).first() as String

    val dbFilePath = "$documentDirectory/mahasiswa.db"

    return Room.databaseBuilder<MahasiswaDb>(
        name = dbFilePath
    )
}