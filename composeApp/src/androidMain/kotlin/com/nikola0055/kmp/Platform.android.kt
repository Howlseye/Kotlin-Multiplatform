package com.nikola0055.kmp

import android.content.Intent
import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()
actual fun shareData(message: String) {
    val context = MainActivity.getAppContext()

    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    val chooser = Intent.createChooser(shareIntent, "Share via")
    chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(chooser)
}