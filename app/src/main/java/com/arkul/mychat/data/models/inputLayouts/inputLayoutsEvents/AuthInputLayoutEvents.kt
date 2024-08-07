package com.arkul.mychat.data.models.inputLayouts.inputLayoutsEvents

sealed class AuthInputLayoutEvents {
    data class EmailChanged(val email: String): AuthInputLayoutEvents()
    data class PasswordChanged(val password: String): AuthInputLayoutEvents()
    data class PasswordRepeatedChanged(val passwordRepeated: String): AuthInputLayoutEvents()
}
