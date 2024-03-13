package com.arkul.mychat.data.models

sealed class AuthOnEvent {
    data class EmailChanged(val email: String): AuthOnEvent()
    data class PasswordChanged(val password: String): AuthOnEvent()
    data class PasswordRepeatedChanged(val passwordRepeated: String): AuthOnEvent()
    data object Submit: AuthOnEvent()
}
