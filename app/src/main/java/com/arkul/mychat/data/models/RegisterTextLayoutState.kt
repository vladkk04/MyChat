package com.arkul.mychat.data.models

data class RegisterTextLayoutState(
    val email: String = "",
    val password: String = "",
    val passwordRepeated: String = "",
    val errorEmail: String? = null,
    val errorPassword: String? = null,
    val errorPasswordRepeated: String? = null,
)

