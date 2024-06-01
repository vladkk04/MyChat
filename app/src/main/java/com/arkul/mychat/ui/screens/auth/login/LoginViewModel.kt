package com.arkul.mychat.ui.screens.auth.login

import androidx.lifecycle.viewModelScope
import com.arkul.mychat.data.models.AuthInputLayoutEvents
import com.arkul.mychat.data.models.LoginTextLayoutState
import com.arkul.mychat.data.models.LoginUiState
import com.arkul.mychat.data.models.auth.AuthCredentialProviderResult
import com.arkul.mychat.data.models.auth.AuthCredentialNotProviderResult
import com.arkul.mychat.data.network.firebase.services.CredentialService
import com.arkul.mychat.ui.navigation.models.NavigationEvent
import com.arkul.mychat.ui.screens.auth.SharedAuthViewModel
import com.arkul.mychat.ui.screens.initial.InitialFragmentDirections
import com.arkul.mychat.utilities.validator.EmailValidate
import com.arkul.mychat.utilities.validator.PasswordValidate
import com.arkul.mychat.utilities.validator.models.ValidationResult
import com.google.firebase.auth.EmailAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val credentialService: CredentialService,
) : SharedAuthViewModel(credentialService) {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState get() = _uiState.asStateFlow()

    private val _stateTextLayout = MutableStateFlow(LoginTextLayoutState())
    val stateTextLayout get() = _stateTextLayout.asStateFlow()

    fun signUpWithProvider(credentialResult: AuthCredentialProviderResult) {
        signInWithProvider(authCredential = credentialResult, onFailure = { errorMessage ->
            _uiState.update {
                it.copy(
                    errorMessage = errorMessage
                )
            }
        })
    }

    fun signUpWithCredential(credentialResult: AuthCredentialNotProviderResult) {
        signInWithCredential(credentialResult, onFailure = { errorMessage ->
            _uiState.update {
                it.copy(
                    errorMessage = errorMessage
                )
            }
        })
    }


    fun signInWithEmail() = viewModelScope.launch {
        if (isValidatedInputs()) {
            _uiState.update {
                it.copy(isLoading = true)
            }
            val emailCredential = EmailAuthProvider.getCredential(
                _stateTextLayout.value.email, _stateTextLayout.value.password
            )

            credentialService.signInWithCredential(emailCredential).let { result ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = result.errorMessage,
                    )
                }
            }
        }
    }

    fun onEvent(event: AuthInputLayoutEvents) {
        when (event) {
            is AuthInputLayoutEvents.EmailChanged -> _stateTextLayout.update {
                it.copy(
                    email = event.email
                )
            }

            is AuthInputLayoutEvents.PasswordChanged -> _stateTextLayout.update {
                it.copy(
                    password = event.password
                )
            }

            else -> {
                return
            }
        }
    }

    fun navigateToForgetPassword() {
        navigateTo(
            NavigationEvent.OnNavigateTo(
                InitialFragmentDirections.actionInitialFragmentToForgetPasswordFragment(
                    _stateTextLayout.value.email
                )
            )
        )
    }

    private fun isValidatedInputs(): Boolean {
        val emailValidateResult = EmailValidate(_stateTextLayout.value.email).validate()
        val passwordValidateResult = PasswordValidate(_stateTextLayout.value.password).validate()

        return !ValidationResult.isValidate(emailValidateResult, passwordValidateResult).apply {
            _stateTextLayout.update { state ->
                state.copy(
                    errorEmail = if (this) emailValidateResult.errorMessage else null,
                    errorPassword = if (this) passwordValidateResult.errorMessage else null,
                )
            }
        }
    }
}