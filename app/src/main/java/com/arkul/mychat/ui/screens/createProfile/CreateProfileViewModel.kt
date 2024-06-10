package com.arkul.mychat.ui.screens.createProfile

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.arkul.mychat.data.local.datastore.DataStoreManager
import com.arkul.mychat.ui.navigation.BaseViewModel
import com.arkul.mychat.ui.navigation.models.NavigationEvent
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateProfileViewModel @Inject constructor(
    private val dataStore: DataStoreManager
) : BaseViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.setHasProfile(false)
        }
    }

    fun cancelCreatingProfile() = viewModelScope.launch(Dispatchers.IO) {
        Firebase.auth.currentUser?.delete()
        navigateTo(NavigationEvent.OnNavigateTo(CreateProfileFragmentDirections.actionCreateProfileFragmentToInitialFragment()))
        dataStore.setHasProfile(null)
    }
    fun createProfile() = viewModelScope.launch(Dispatchers.IO) {
        //navigateTo(NavigationEvent.OnNavigateTo(CreateProfileFragmentDirections.actionCreateProfileFragmentToCorrespondencesFragment()))
        dataStore.setHasProfile(true)
    }
}