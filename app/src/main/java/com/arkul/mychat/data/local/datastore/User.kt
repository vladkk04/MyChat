package com.arkul.mychat.data.local.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey


class User {
    companion object {
        val HAS_PROFILE
            get() = booleanPreferencesKey("hasProfile")
    }
}