@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.nikola0055.kmp.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.nikola0055.kmp.model.Mahasiswa
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.SynchronizedObject
import kotlinx.coroutines.internal.synchronized
import kotlin.concurrent.Volatile

@Database(entities = [Mahasiswa::class], version = 1, exportSchema = false)
@ConstructedBy(MahasiswaDbConstructor::class)
abstract class MahasiswaDb : RoomDatabase() {
    abstract val mahasiswaDao: MahasiswaDao

    companion object {
        @Volatile
        private var INSTANCE: MahasiswaDb? = null
        @OptIn(InternalCoroutinesApi::class)
        private val lock = SynchronizedObject()

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(builder: Builder<MahasiswaDb>): MahasiswaDb {
            return INSTANCE ?: synchronized(lock) {
                val instance = INSTANCE ?: builder
                    .setDriver(BundledSQLiteDriver())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

@Suppress("KotlinNoActualForExpect")
expect object MahasiswaDbConstructor : RoomDatabaseConstructor<MahasiswaDb> {
    override fun initialize(): MahasiswaDb
}