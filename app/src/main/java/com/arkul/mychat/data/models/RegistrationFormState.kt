package com.arkul.mychat.data.models

data class RegistrationFormState(
    val email: String = "",
    val password: String = "",
    val passwordRepeated: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val passwordRepeatedError: String? = null,
)

