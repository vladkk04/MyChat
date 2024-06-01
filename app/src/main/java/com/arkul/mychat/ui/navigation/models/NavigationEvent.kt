package com.arkul.mychat.ui.navigation.models

import android.os.Bundle
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions


sealed class NavigationEvent {
    data class OnNavigateTo(
        val directions: NavDirections,
        val navOptions: NavOptions? = null
    ) : NavigationEvent()

    data object OnNavigateBack : NavigationEvent()

}

