package com.arkul.mychat.ui.screens.auth

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.arkul.mychat.data.models.auth.AuthCredentialProviderResult
import com.arkul.mychat.data.models.auth.AuthCredentialNotProviderResult
import com.arkul.mychat.data.network.firebase.services.CredentialService
import com.arkul.mychat.ui.navigation.BaseViewModel
import com.arkul.mychat.ui.navigation.models.NavigationEvent
import com.arkul.mychat.ui.screens.initial.InitialFragmentDirections
import com.google.firebase.auth.EmailAuthProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class SharedAuthViewModel(
    private val credentialService: CredentialService,
) : BaseViewModel() {
    private fun checkIsEmailVerified(): Boolean? {
        with(credentialService.accountService.currentUser) {
            this?.reload()
            return this?.isEmailVerified
        }
    }

    protected fun signInWithCredential(
        authCredentialNotProviderResult: AuthCredentialNotProviderResult,
        onSuccess: (() -> Unit)? = null,
        onFailure: ((errorMessage: String?) -> Unit)? = null
    ) = viewModelScope.launch {
        if (authCredentialNotProviderResult.errorMessage != null) {
            onFailure?.invoke(authCredentialNotProviderResult.errorMessage)
            return@launch
        }

        val authResult =
            authCredentialNotProviderResult.credential?.let {
                credentialService.signInWithCredential(it)
            }

        if (authResult?.errorMessage != null) {
            onFailure?.invoke(authResult.errorMessage)
            return@launch
        }

        if (checkIsEmailVerified() == false) {
            onSuccess?.invoke()
            navigateToEmailVerification()
            return@launch
        }

        authResult?.data.let {
            if (it?.isNewUser == true || authCredentialNotProviderResult.credential?.signInMethod == "password") {
                navigateToCreateProfile()
                onSuccess?.invoke()
            }
        }
    }

    protected fun signInWithProvider(
        authCredential: AuthCredentialProviderResult,
        onFailure: ((errorMessage: String?) -> Unit)? = null
    ) {
        authCredential.data?.let {
            if (it.isNewUser == true) {
                navigateToCreateProfile()
            }
        }
    }

    private fun navigateToEmailVerification() {
        navigateTo(
            NavigationEvent.OnNavigateTo(
                InitialFragmentDirections.actionInitialFragmentToWaitingVerifyEmail()
            )
        )
    }

    private fun  navigateToCreateProfile() {
        navigateTo(
            NavigationEvent.OnNavigateTo(
                InitialFragmentDirections.actionInitialFragmentToCreateProfileFragment()
            )
        )
    }
}