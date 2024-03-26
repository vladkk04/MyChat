package com.arkul.mychat.data.models

data class LoginUiState (
    val isLoading:Boolean = false,
    val isUserLoggedIn: Boolean = false,
    val errorMessage: String? = null
)