package com.nikola0055.kmp

import androidx.room.Room
import androidx.room.RoomDatabase
import com.nikola0055.kmp.database.CatatanDb
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun getDatabaseBuilder(): RoomDatabase.Builder<CatatanDb> {
    val documentDirectory = NSSearchPathForDirectoriesInDomains(
        NSDocumentDirectory,
        NSUserDomainMask,
        true
    ).first() as String

    val dbFilePath = "$documentDirectory/catatan.db"

    return Room.databaseBuilder<CatatanDb>(
        name = dbFilePath
    )
}