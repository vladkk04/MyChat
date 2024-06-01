package com.arkul.mychat.data.network.firebase.services

import com.arkul.mychat.data.models.auth.AuthCredentialProviderResult
import com.google.firebase.auth.AuthCredential

interface CredentialService {
    val accountService: AccountService
    suspend fun signInWithCredential(credential: AuthCredential): AuthCredentialProviderResult
    suspend fun linkWithCredential(credential: AuthCredential): AuthCredentialProviderResult
    suspend fun unlinkCredential(credential: String): AuthCredentialProviderResult
}