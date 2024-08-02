package com.arkul.mychat.data.models.uiStates

data class LoginUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)