package com.arkul.mychat.ui.screens.initial

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkul.mychat.data.local.datastore.DataStoreManager
import com.arkul.mychat.ui.navigation.BaseViewModel
import com.arkul.mychat.ui.navigation.models.NavigationEvent
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitialViewModel @Inject constructor(
    private val dataRepository: DataStoreManager
) : BaseViewModel() {

}