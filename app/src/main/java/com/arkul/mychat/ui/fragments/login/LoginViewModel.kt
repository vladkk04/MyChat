package com.arkul.mychat.ui.fragments.login

import androidx.lifecycle.viewModelScope
import com.arkul.mychat.data.models.AuthInputLayoutEvents
import com.arkul.mychat.data.models.LoginTextLayoutState
import com.arkul.mychat.data.models.LoginUiState
import com.arkul.mychat.data.models.auth.CredentialResult
import com.arkul.mychat.data.network.firebase.services.CredentialService
import com.arkul.mychat.ui.fragments.forgetPassword.ForgetPasswordFragment
import com.arkul.mychat.ui.navigation.BaseViewModel
import com.arkul.mychat.ui.navigation.NavigationEvent
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
) : BaseViewModel() {

    private val _stateTextLayout = MutableStateFlow(LoginTextLayoutState())
    val stateTextLayout get() = _stateTextLayout.asStateFlow()

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState get() = _uiState.asStateFlow()

    fun onForgetPassword() {
        navigate(NavigationEvent.OnNavigateTo(ForgetPasswordFragment()))
    }

    fun signInWithEmail() = viewModelScope.launch {
        if (!isValidatedInputs()) {
            val emailCredential = EmailAuthProvider.getCredential(
                _stateTextLayout.value.email,
                _stateTextLayout.value.password
            )
            credentialService.signInWithCredential(emailCredential)
        }
    }

    fun signInWithCredential(credentialResult: CredentialResult) = viewModelScope.launch {
        if (credentialResult.errorMessage != null) {
            _uiState.update {
                it.copy(
                    errorMessage = credentialResult.errorMessage
                )
            }
            return@launch
        }

        val authResult =
            credentialResult.credential?.let { credentialService.signInWithCredential(it) }

        _uiState.update {
            it.copy(
                errorMessage = authResult?.errorMessage
            )
        }
    }

    fun onEvent(event: AuthInputLayoutEvents) {
        when (event) {
            is AuthInputLayoutEvents.EmailChanged -> _stateTextLayout.update { it.copy(email = event.email) }
            is AuthInputLayoutEvents.PasswordChanged -> _stateTextLayout.update { it.copy(password = event.password) }
            else -> {
                return
            }
        }
    }

    private fun isValidatedInputs(): Boolean {
        val emailValidateResult = EmailValidate(_stateTextLayout.value.email).validate()
        val passwordValidateResult = PasswordValidate(_stateTextLayout.value.email).validate()

        return ValidationResult.isValidate(emailValidateResult, passwordValidateResult).apply {
            if (this) {
                _stateTextLayout.update { state ->
                    state.copy(
                        errorEmail = emailValidateResult.errorMessage,
                        errorPassword = passwordValidateResult.errorMessage,
                    )
                }
            }
        }
    }
}