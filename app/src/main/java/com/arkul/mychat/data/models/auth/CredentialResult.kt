package com.arkul.mychat.data.models.auth

import com.google.firebase.auth.AuthCredential

data class CredentialResult(
    val credential: AuthCredential? = null,
    val errorMessage: String? = null
)

