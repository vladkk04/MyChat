package com.arkul.mychat.data.network.firebase.services

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface CredentialService {
    val accountService: AccountService
    suspend fun signInWithCredential(credential: AuthCredential)
    suspend fun linkWithCredential(credential: AuthCredential)
    suspend fun unlinkCredential(credential: String)
}