package com.arkul.mychat.ui.navigation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.arkul.mychat.R
import com.arkul.mychat.ui.fragments.forgetPassword.ForgetPasswordFragment
import com.arkul.mychat.ui.fragments.login.LoginFragment
import com.arkul.mychat.ui.fragments.waitingVerify.WaitingVerifyEmail

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
