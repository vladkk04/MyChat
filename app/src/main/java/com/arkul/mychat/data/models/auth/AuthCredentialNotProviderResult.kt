package com.arkul.mychat.data.models.auth

import com.google.firebase.auth.AuthCredential

data class AuthCredentialNotProviderResult(
    val credential: AuthCredential? = null,
    val errorMessage: String? = null
)

