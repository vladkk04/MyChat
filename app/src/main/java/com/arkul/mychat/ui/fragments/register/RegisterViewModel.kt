package com.arkul.mychat.ui.fragments.register

import androidx.lifecycle.viewModelScope
import com.arkul.mychat.data.models.AuthInputLayoutEvents
import com.arkul.mychat.data.models.RegisterTextLayoutState
import com.arkul.mychat.data.models.RegisterUiState
import com.arkul.mychat.data.models.auth.CredentialResult
import com.arkul.mychat.data.network.firebase.services.CredentialService
import com.arkul.mychat.data.network.firebase.services.EmailService
import com.arkul.mychat.ui.fragments.waitingVerify.WaitingVerifyEmail
import com.arkul.mychat.ui.navigation.BaseViewModel
import com.arkul.mychat.ui.navigation.NavigationEvent
import com.arkul.mychat.utilities.validator.EmailValidate
import com.arkul.mychat.utilities.validator.PasswordRepeatedValidate
import com.arkul.mychat.utilities.validator.PasswordValidate
import com.arkul.mychat.utilities.validator.models.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val emailService: EmailService,
    private val credentialService: CredentialService,
) : BaseViewModel() {

    private val _stateTextLayout = MutableStateFlow(RegisterTextLayoutState())
    val stateTextLayout get() = _stateTextLayout.asStateFlow()

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState get() = _uiState.asStateFlow()

    fun signUpWithEmail() = viewModelScope.launch {
        onWaitingVerifyEmail()
        if (!isValidatedInputs()) {
            val result = emailService.createUserWithEmailAndPassword(
                _stateTextLayout.value.email,
                _stateTextLayout.value.password
            )
            if (result.isSuccess) {

            }
        }
    }

    private fun onWaitingVerifyEmail() {
        navigate(NavigationEvent.OnNavigateTo(WaitingVerifyEmail()))
    }

    fun signUpWithCredential(credentialResult: CredentialResult) = viewModelScope.launch {
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
            is AuthInputLayoutEvents.PasswordRepeatedChanged -> _stateTextLayout.update {
                it.copy(
                    passwordRepeated = event.passwordRepeated
                )
            }
        }
    }

    private fun isValidatedInputs(): Boolean {
        val emailValidate = EmailValidate(_stateTextLayout.value.email).validate()
        val passwordValidate = PasswordValidate(_stateTextLayout.value.password).validate()
        val passwordRepeatedValidate = PasswordRepeatedValidate(
            _stateTextLayout.value.password,
            _stateTextLayout.value.passwordRepeated
        ).validate()

        return ValidationResult.isValidate(
            emailValidate,
            passwordValidate,
            passwordRepeatedValidate
        ).apply {
            if (this) {
                _stateTextLayout.update { state ->
                    state.copy(
                        errorEmail = emailValidate.errorMessage,
                        errorPassword = passwordValidate.errorMessage,
                        errorPasswordRepeated = passwordRepeatedValidate.errorMessage
                    )
                }
            }
        }
    }

}