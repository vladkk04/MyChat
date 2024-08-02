package com.arkul.mychat.data.models.inputLayouts.inputLayoutsState

data class LoginInputLayoutState(
    val email: String = "",
    val password: String = "",
    val errorEmail: String? = null,
    val errorPassword: String? = null,
)