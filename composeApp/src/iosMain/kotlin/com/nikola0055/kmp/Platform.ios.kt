package com.nikola0055.kmp

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

@OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
actual fun createDataStore(): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            val documentDirectory = NSSearchPathForDirectoriesInDomains(
                NSDocumentDirectory,
                NSUserDomainMask,
                true
            ).first() as String

            "$documentDirectory/$DATASTORE_FILE_NAME".toPath()
        }
    )
}