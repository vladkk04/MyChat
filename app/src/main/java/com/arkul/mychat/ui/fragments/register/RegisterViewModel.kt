package com.arkul.mychat.ui.fragments.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkul.mychat.data.models.RegistrationFormState
import com.arkul.mychat.data.models.RegistrationFormState.RegistrationOnEvent
import com.arkul.mychat.data.network.firebase.services.AccountService
import com.arkul.mychat.data.network.firebase.services.CredentialService
import com.arkul.mychat.data.network.firebase.services.EmailService
import com.arkul.mychat.utilities.validator.EmailValidate
import com.arkul.mychat.utilities.validator.PasswordRepeatedValidate
import com.arkul.mychat.utilities.validator.PasswordValidate
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

    private fun signUp() = viewModelScope.launch {

        val credential = EmailAuthProvider.getCredential("val@gmail.com", "12345678V")


        if (!checkValidationInputData()) {
            //accountService.signUp(_stateTextLayout.value.email, _stateTextLayout.value.password)
        }
    }

    fun onEvent(event: RegistrationOnEvent) {
        when (event) {
            is RegistrationOnEvent.EmailChanged -> _stateTextLayout.update { it.copy(email = event.email) }
            is RegistrationOnEvent.PasswordChanged -> _stateTextLayout.update { it.copy(password = event.password) }
            is RegistrationOnEvent.PasswordRepeatedChanged -> _stateTextLayout.update { it.copy(passwordRepeated = event.passwordRepeated) }
            is RegistrationOnEvent.Submit -> signUp()
        }
    }

    private fun checkValidationInputData(): Boolean {
        val email = EmailValidate(_stateTextLayout.value.email).validate()
        val password = PasswordValidate(_stateTextLayout.value.password).validate()
        val passwordRepeated = PasswordRepeatedValidate(_stateTextLayout.value.password, _stateTextLayout.value.passwordRepeated).validate()

        val hasError = listOf(
            email,
            password,
            passwordRepeated
        ).any { !it.isSuccess }

        if (hasError) {
            _stateTextLayout.update {
                it.copy(
                    emailError = email.errorMessage,
                    passwordError = password.errorMessage,
                    passwordRepeatedError = passwordRepeated.errorMessage
                )
            }
        }

        return hasError
    }
}