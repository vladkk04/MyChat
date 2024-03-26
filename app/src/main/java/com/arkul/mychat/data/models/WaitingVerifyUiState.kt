package com.arkul.mychat.data.models

data class WaitingVerifyUiState(
    val isResendConfirmation: Boolean = true,
    val errorMessage: String? = null
)
