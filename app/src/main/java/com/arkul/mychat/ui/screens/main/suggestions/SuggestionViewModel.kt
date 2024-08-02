package com.arkul.mychat.ui.screens.main.suggestions

import com.arkul.mychat.ui.navigation.BaseViewModel
import com.arkul.mychat.ui.navigation.models.NavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SuggestionViewModel @Inject constructor() : BaseViewModel() {

    fun sendSuggestionToChat() {
        navigateTo(NavigationEvent.OnNavigateTo(SuggestionFragmentDirections.actionSuggestionFragmentToInboxChatsFragment()))
    }

    fun openAuthorProfile() {
        navigateTo(NavigationEvent.OnNavigateTo(SuggestionFragmentDirections.actionSuggestionFragmentToProfileFragment()))
    }

}