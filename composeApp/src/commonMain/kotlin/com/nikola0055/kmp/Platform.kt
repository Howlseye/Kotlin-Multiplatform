package com.nikola0055.kmp

import androidx.room.RoomDatabase
import com.nikola0055.kmp.database.MahasiswaDb

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun getDatabaseBuilder(): RoomDatabase.Builder<MahasiswaDb>
