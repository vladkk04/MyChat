package com.arkul.mychat.ui.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arkul.mychat.R


sealed class NavigationEvent {
    data class OnNavigateTo(
        val fragment: Fragment,
        val addToBackStack: Boolean = true
    ): NavigationEvent()

}

