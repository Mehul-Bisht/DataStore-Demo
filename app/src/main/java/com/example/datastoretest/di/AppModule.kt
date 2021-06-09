package com.example.datastoretest.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.datastoretest.datastore.DataStorePreferenceStorage
import com.example.datastoretest.datastore.DataStorePreferenceStorage.Companion.PREFS_NAME
import com.example.datastoretest.datastore.PreferenceStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    val Context.datastore by preferencesDataStore(
        name = DataStorePreferenceStorage.PREFS_NAME,
        produceMigrations = { context ->
            listOf(
                SharedPreferencesMigration(
                    context,
                    DataStorePreferenceStorage.PREFS_NAME
                )
            )
        }
    )

    @Singleton
    @Provides
    fun providePreferencesStorage(@ApplicationContext context: Context): PreferenceStorage =
        DataStorePreferenceStorage(context.datastore)

    @Singleton
    @Provides
    fun provideDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> {

        val datastore = PreferenceDataStoreFactory.create(
            migrations = listOf(SharedPreferencesMigration(context, PREFS_NAME)),
            scope = CoroutineScope(Dispatchers.Default)
        ) {
            context.preferencesDataStoreFile(PREFS_NAME)
        }
        return datastore
    }
}