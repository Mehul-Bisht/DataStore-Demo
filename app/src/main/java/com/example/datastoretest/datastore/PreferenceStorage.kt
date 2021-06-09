package com.example.datastoretest.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.flow.Flow

interface PreferenceStorage {

    suspend fun save(isLoggedIn: Boolean)
    val isLoggedIn: Flow<Boolean>

    object PreferencesKey{

        val PREF_LOGGED_IN = booleanPreferencesKey("logged_in")
    }
}