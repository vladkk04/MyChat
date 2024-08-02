package com.arkul.mychat.data.models.auth

data class AuthEmailResult(
    val isSuccess: Boolean = false,
    val isVerifyEmail: Boolean? = false,
    val errorMessage: String? = null
)
