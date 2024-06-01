package com.arkul.mychat.data.models

data class WaitingVerifyUiState(
    val shouldResendConfirmation: Boolean = false,
    val errorMessage: String? = null
)
