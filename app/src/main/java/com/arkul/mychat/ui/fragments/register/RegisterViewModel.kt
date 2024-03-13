package com.arkul.mychat.ui.fragments.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkul.mychat.data.models.AuthOnEvent
import com.arkul.mychat.data.models.RegistrationFormState
import com.arkul.mychat.data.network.firebase.services.CredentialService
import com.arkul.mychat.data.network.firebase.services.EmailService
import com.arkul.mychat.utilities.validator.EmailValidate
import com.arkul.mychat.utilities.validator.PasswordRepeatedValidate
import com.arkul.mychat.utilities.validator.PasswordValidate
import com.arkul.mychat.utilities.validator.base.InputValidator
import com.google.firebase.auth.EmailAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val credentialService: CredentialService,
    private val emailService: EmailService
) : ViewModel() {

    private val _stateTextLayout = MutableStateFlow(RegistrationFormState())
    val stateTextLayout get() = _stateTextLayout.asStateFlow()

    private val inputValidator by lazy { InputValidator() }

    private fun signUp() = viewModelScope.launch {
        if (!isValidationInputs()) {
            emailService.signUpWithEmail(_stateTextLayout.value.email, _stateTextLayout.value.password)
        }
    }

    fun onEvent(event: AuthOnEvent) {
        when (event) {
            is AuthOnEvent.EmailChanged -> _stateTextLayout.update { it.copy(email = event.email) }
            is AuthOnEvent.PasswordChanged -> _stateTextLayout.update { it.copy(password = event.password) }
            is AuthOnEvent.PasswordRepeatedChanged -> _stateTextLayout.update { it.copy(passwordRepeated = event.passwordRepeated) }
            AuthOnEvent.Submit -> signUp()
        }
    }

    private fun isValidationInputs(): Boolean {
        return inputValidator.checkValidation(
            EmailValidate(_stateTextLayout.value.email),
            PasswordValidate(_stateTextLayout.value.password),
            PasswordRepeatedValidate(_stateTextLayout.value.password, _stateTextLayout.value.passwordRepeated)
        ).apply {
            if (this) {
                _stateTextLayout.update { state ->
                    state.copy(
                        emailError = inputValidator.emailError,
                        passwordError = inputValidator.passwordError,
                        passwordRepeatedError = inputValidator.passwordRepeatedError
                    )
                }
            }
        }
    }
}