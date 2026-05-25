package com.nikola0055.kmp

import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun shareData(message: String) {
    val window = UIApplication.sharedApplication.keyWindow
    val rootViewController = window?.rootViewController

    val activityController = UIActivityViewController(
        activityItems = listOf(message),
        applicationActivities = null
    )

    rootViewController?.presentViewController(
        viewControllerToPresent = activityController,
        animated = true,
        completion = null
    )
}