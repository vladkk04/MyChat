package com.arkul.mychat.data.models.auth

data class AuthCredentialResult(
    val data: UserData? = null,
    val errorMessage: String? = null
)

data class UserData(
    val userId: String? = null,
    val displayName: String? = null,
    val phoneNumber: String? = null
)
