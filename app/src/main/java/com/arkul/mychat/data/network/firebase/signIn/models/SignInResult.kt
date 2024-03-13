package com.arkul.mychat.data.network.firebase.signIn.models

import androidx.credentials.Credential

data class SignInResult(
    val credential: Credential,
    val errorMessage: String? = null
)
