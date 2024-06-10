package com.arkul.mychat

import android.Manifest
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PermissionResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkul.mychat.data.local.datastore.DataStoreManager
import com.arkul.mychat.ui.navigation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import java.security.Permission
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStore: DataStoreManager
): ViewModel() {

    suspend fun getHasUserProfile(): Boolean? {
        //dataStore.setHasProfile(null)
        return dataStore.hasProfile.first()
    }
}