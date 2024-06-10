package com.arkul.mychat.data.models

data class WaitingVerifyUiState(
    val userHasConfirmed: Boolean = false,
    val shouldResendConfirmation: Boolean = false,
    val errorMessage: String? = null
)
