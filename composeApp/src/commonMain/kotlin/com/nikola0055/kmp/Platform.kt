package com.nikola0055.kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform