package com.arkul.mychat.data.models

data class RegistrationFormState(
    val email: String = "",
    val password: String = "",
    val passwordRepeated: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val passwordRepeatedError: String? = null,
) {
    sealed class RegistrationOnEvent {
        data class EmailChanged(val email: String): RegistrationOnEvent()
        data class PasswordChanged(val password: String): RegistrationOnEvent()
        data class PasswordRepeatedChanged(val passwordRepeated: String): RegistrationOnEvent()
        data object Submit: RegistrationOnEvent()
    }
}

