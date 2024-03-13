package com.arkul.mychat.ui.fragments.login

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkul.mychat.data.models.LoginFormState
import com.arkul.mychat.data.network.firebase.AuthMethod
import com.arkul.mychat.data.network.firebase.services.CredentialService
import com.arkul.mychat.data.network.firebase.services.EmailService
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val credentialService: CredentialService,
    private val emailService: EmailService
): ViewModel() {
    private val _state = MutableStateFlow(LoginFormState())
    val state get() = _state

    fun signIn(authMethod: AuthMethod) {
        when (authMethod) {
            is AuthMethod.Email -> {
                signInWithEmail(email = _state.value.email, password = _state.value.password)
            }
            is AuthMethod.GitHub -> {
                signInWithGitHub(authMethod.activity)
            }
            is AuthMethod.Google -> {
                signInWithGoogle(authMethod.googleIdTokenCredential)
            }
        }
    }

    private fun signInWithEmail(email: String, password: String) = viewModelScope.launch {
        emailService.signInWithEmail(email, password)
    }

    private fun signInWithGitHub(activity: Activity) = viewModelScope.launch {
        credentialService.accountService.apply {
            this.firebaseAuth.startActivityForSignInWithProvider(activity, this.providerOAuthProviderGitHub).await()
        }
    }

    private fun signInWithGoogle(googleIdTokenCredential: GoogleIdTokenCredential) = viewModelScope.launch {
        GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null).apply {
            credentialService.signInWithCredential(this)
        }
    }
}