package com.arkul.mychat.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext appContext: Context) {

    private val userDataStore: DataStore<Preferences> = appContext.dataStore

    suspend fun setHasProfile(value: Boolean?) {
        userDataStore.edit { preferences ->
            if (value == null) {
                preferences.remove(User.HAS_PROFILE)
                return@edit
            }
            preferences[User.HAS_PROFILE] = value
        }
    }

    val hasProfile: Flow<Boolean?> = userDataStore.data.catch { e ->
        when (e) {
            is IOException -> emit(emptyPreferences())
            else -> throw e
        }
    }.map { preferences -> preferences[User.HAS_PROFILE] }
}