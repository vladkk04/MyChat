package com.arkul.mychat.data.network.firebase.services

import com.arkul.mychat.data.models.auth.AuthCredentialResult
import com.google.firebase.auth.AuthCredential

interface CredentialService {
    val accountService: AccountService
    suspend fun signInWithCredential(credential: AuthCredential): AuthCredentialResult
    suspend fun linkWithCredential(credential: AuthCredential): AuthCredentialResult
    suspend fun unlinkCredential(credential: String): AuthCredentialResult
}