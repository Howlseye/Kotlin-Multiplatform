package com.nikola0055.kmp.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.internal.SynchronizedObject
import kotlinx.coroutines.internal.synchronized
import kotlin.concurrent.Volatile

class SettingsDataStore private constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        private val IS_LIST = booleanPreferencesKey("is_list")

        @Volatile
        private var INSTANCE: SettingsDataStore? = null
        @OptIn(InternalCoroutinesApi::class)
        private val lock = SynchronizedObject()

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(produceDataStore: () -> DataStore<Preferences>): SettingsDataStore {
            return INSTANCE ?: synchronized(lock) {
                INSTANCE ?: SettingsDataStore(produceDataStore()).also { INSTANCE = it }
            }
        }
    }

    val layoutFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_LIST] ?: true
    }

    suspend fun saveLayout(isList: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LIST] = isList
        }
    }
}