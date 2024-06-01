package com.arkul.mychat.ui.screens.auth

import androidx.lifecycle.viewModelScope
import com.arkul.mychat.data.models.auth.AuthCredentialProviderResult
import com.arkul.mychat.data.models.auth.AuthCredentialNotProviderResult
import com.arkul.mychat.data.network.firebase.services.CredentialService
import com.arkul.mychat.ui.navigation.BaseViewModel
import com.arkul.mychat.ui.navigation.models.NavigationEvent
import com.arkul.mychat.ui.screens.initial.InitialFragmentDirections
import kotlinx.coroutines.launch

open class SharedAuthViewModel(
    private val credentialService: CredentialService,
) : BaseViewModel() {
    fun signInWithCredential(
        authCredentialNotProviderResult: AuthCredentialNotProviderResult,
        onFailure: ((errorMessage: String?) -> Unit)? = null
    ) = viewModelScope.launch {
            if (authCredentialNotProviderResult.errorMessage != null) {
                onFailure?.invoke(authCredentialNotProviderResult.errorMessage)
                return@launch
            }

            val authResult =
                authCredentialNotProviderResult.credential?.let { credentialService.signInWithCredential(it) }

            authResult?.data?.let {
                if (it.isNewUser == true) {
                    navigateToCreateProfile(it.displayName, it.username)
                }
                onFailure?.invoke(authResult.errorMessage)
            }
        }

    fun signInWithProvider(
        authCredential: AuthCredentialProviderResult,
        onFailure: ((errorMessage: String?) -> Unit)? = null
    ) {
        authCredential.data?.let {
            if (it.isNewUser == true) {
                navigateToCreateProfile(it.displayName, it.username)
            }
        }
    }

    private fun navigateToCreateProfile(displayName: String?, username: String?) {
        navigateTo(
            NavigationEvent.OnNavigateTo(
                InitialFragmentDirections.actionInitialFragmentToCreateProfileFragment(
                    displayName,
                    username
                )
            )
        )
    }
}