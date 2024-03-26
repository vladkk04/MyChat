package com.arkul.mychat.data.models

data class LoginTextLayoutState(
    val email: String = "",
    val password: String = "",
    val errorEmail: String? = null,
    val errorPassword: String? = null,
)