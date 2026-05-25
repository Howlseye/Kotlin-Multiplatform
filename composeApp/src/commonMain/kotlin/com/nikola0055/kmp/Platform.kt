package com.nikola0055.kmp

import androidx.room.RoomDatabase
import com.nikola0055.kmp.database.CatatanDb

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun getDatabaseBuilder(): RoomDatabase.Builder<CatatanDb>
