package com.arkul.mychat.data.models.auth

data class AuthCredentialProviderResult(
    val data: UserData? = null,
    val errorMessage: String? = null
)

data class UserData(
    val username: String? = null,
    val displayName: String? = null,
    val isNewUser: Boolean? = null
)
