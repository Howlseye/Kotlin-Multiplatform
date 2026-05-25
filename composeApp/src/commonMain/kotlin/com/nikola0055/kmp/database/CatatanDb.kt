@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.nikola0055.kmp.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.nikola0055.kmp.model.Catatan
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.SynchronizedObject
import kotlinx.coroutines.internal.synchronized
import kotlin.concurrent.Volatile

@Database(entities = [Catatan::class], version = 1, exportSchema = false)
@ConstructedBy(CatatanDbConstructor::class)
abstract class CatatanDb : RoomDatabase() {
    abstract val catatanDao: CatatanDao

    companion object {
        @Volatile
        private var INSTANCE: CatatanDb? = null
        @OptIn(InternalCoroutinesApi::class)
        private val lock = SynchronizedObject()

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(builder: Builder<CatatanDb>): CatatanDb {
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
expect object CatatanDbConstructor : RoomDatabaseConstructor<CatatanDb> {
    override fun initialize(): CatatanDb
}