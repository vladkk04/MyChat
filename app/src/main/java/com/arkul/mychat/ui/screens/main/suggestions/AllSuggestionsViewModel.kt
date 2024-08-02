package com.arkul.mychat.ui.screens.main.suggestions

import com.arkul.mychat.data.models.Suggestion
import com.arkul.mychat.ui.navigation.BaseViewModel
import com.arkul.mychat.ui.navigation.models.NavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllSuggestionsViewModel @Inject constructor(): BaseViewModel() {

    fun navigateToSuggestion(suggestion: Suggestion) {
        navigateTo(
            NavigationEvent.OnNavigateTo(
                AllSuggestionsFragmentDirections.actionSuggestionsFragmentToSuggestionFragment(
                    suggestion
                )
            )
        )
    }

}