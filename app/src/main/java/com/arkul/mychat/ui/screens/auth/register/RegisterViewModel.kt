package com.arkul.mychat.ui.screens.auth.register

import androidx.lifecycle.viewModelScope
import com.arkul.mychat.data.models.AuthInputLayoutEvents
import com.arkul.mychat.data.models.RegisterTextLayoutState
import com.arkul.mychat.data.models.RegisterUiState
import com.arkul.mychat.data.models.auth.AuthCredentialProviderResult
import com.arkul.mychat.data.models.auth.AuthCredentialNotProviderResult
import com.arkul.mychat.data.network.firebase.services.CredentialService
import com.arkul.mychat.data.network.firebase.services.EmailService
import com.arkul.mychat.ui.navigation.models.NavigationEvent
import com.arkul.mychat.ui.screens.auth.SharedAuthViewModel
import com.arkul.mychat.ui.screens.initial.InitialFragmentDirections
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
) : SharedAuthViewModel(credentialService) {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState get() = _uiState.asStateFlow()

    private val _stateTextLayout = MutableStateFlow(RegisterTextLayoutState())
    val stateTextLayout get() = _stateTextLayout.asStateFlow()

    private fun updateIsLoading(isLoading: Boolean) {
        _uiState.update {
            it.copy(
                isLoading = isLoading
            )
        }
    }

    private fun updateErrorMessage(errorMessage: String?) {
        _uiState.update {
            it.copy(
                errorMessage = errorMessage
            )
        }
    }


    fun signUpWithEmail() = viewModelScope.launch {
        if (!isValidatedInputs()) return@launch

        updateIsLoading(true)

        emailService.createUserWithEmailAndPassword(
            _stateTextLayout.value.email,
            _stateTextLayout.value.password
        ).let { result ->
            if (result.isSuccess) {
                navigateToVerifyEmail()
            }
            updateIsLoading(false)
            updateErrorMessage(result.errorMessage.takeIf { !result.isSuccess })
        }
    }



    fun signUpWithCredential(credentialResult: AuthCredentialNotProviderResult) {
        signInWithCredential(credentialResult, onFailure = { errorMessage ->
            updateErrorMessage(errorMessage)
        })
    }

    fun signUpWithProvider(credentialResult: AuthCredentialProviderResult) {
        signInWithProvider(authCredential = credentialResult, onFailure = { errorMessage ->
            updateErrorMessage(errorMessage)
        })
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

    private fun navigateToVerifyEmail() {
        navigateTo(
            NavigationEvent.OnNavigateTo(
                InitialFragmentDirections.actionInitialFragmentToWaitingVerifyEmail()
            )
        )
    }

    private fun isValidatedInputs(): Boolean {
        val emailValidate = EmailValidate(_stateTextLayout.value.email).validate()
        val passwordValidate = PasswordValidate(_stateTextLayout.value.password).validate()
        val passwordRepeatedValidate = PasswordRepeatedValidate(
            _stateTextLayout.value.password,
            _stateTextLayout.value.passwordRepeated
        ).validate()

        return !ValidationResult.isValidate(
            emailValidate,
            passwordValidate,
            passwordRepeatedValidate
        ).apply {
            _stateTextLayout.update { state ->
                state.copy(
                    errorEmail = if (this) emailValidate.errorMessage else null,
                    errorPassword = if (this) passwordValidate.errorMessage else null,
                    errorPasswordRepeated = if (this) passwordRepeatedValidate.errorMessage else null
                )
            }
        }
    }

}