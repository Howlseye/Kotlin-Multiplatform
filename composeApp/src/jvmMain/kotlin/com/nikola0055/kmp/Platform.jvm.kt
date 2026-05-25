package com.nikola0055.kmp

import java.awt.Desktop
import java.net.URI
import java.net.URLEncoder

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun shareData(message: String) {
    if (Desktop.isDesktopSupported()) {
        val desktop = Desktop.getDesktop()
        if (desktop.isSupported(Desktop.Action.MAIL)) {
            val encodedMessage = URLEncoder.encode(message, "UTF-8").replace("+", "%20")
            val uri = URI("mailto:?body=$encodedMessage")
            desktop.mail(uri)
        }
    }
}