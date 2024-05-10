package com.arkul.mychat.ui.navigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks

class NavigationObserver(private val viewModel: BaseViewModel): FragmentLifecycleCallbacks() {
    override fun onFragmentViewCreated(
        fm: FragmentManager,
        fragment: Fragment,
        v: View,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentViewCreated(fm, fragment, v, savedInstanceState)

        viewModel.navigationEvent.observe(fragment.viewLifecycleOwner) {
            when (it) {
                is NavigationEvent.OnNavigateTo -> {

                }
            }
        }
    }

    fun register(fragment: Fragment) {
        //fragment.parentFragmentManagerdf.registerFragmentLifecycleCallbacks(this, true)
    }
}
