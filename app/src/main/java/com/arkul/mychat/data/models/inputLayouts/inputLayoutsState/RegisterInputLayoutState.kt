package com.arkul.mychat.data.models.inputLayouts.inputLayoutsState

data class RegisterInputLayoutState(
    val email: String = "",
    val password: String = "",
    val passwordRepeated: String = "",
    val errorEmail: String? = null,
    val errorPassword: String? = null,
    val errorPasswordRepeated: String? = null,
)

