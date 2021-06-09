package com.example.datastoretest.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.datastoretest.datastore.PreferenceStorage.PreferencesKey.PREF_LOGGED_IN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStorePreferenceStorage @Inject constructor(
    private val dataStore: DataStore<Preferences>
): PreferenceStorage {

    companion object {
        const val PREFS_NAME = "auth_prefs"
    }

    override suspend fun save(isLoggedIn: Boolean) {
        dataStore.edit { settings ->
            settings[PREF_LOGGED_IN] = isLoggedIn
        }
    }

    override val isLoggedIn: Flow<Boolean> =
        dataStore.data.map { it[PREF_LOGGED_IN] ?: false }
}