package com.arkul.mychat.data.network.firebase.services

import com.arkul.mychat.data.models.AuthEmailResult
import com.arkul.mychat.data.models.auth.AuthCredentialResult

interface EmailService {
    val accountService: AccountService
    suspend fun createUserWithEmailAndPassword(email: String, password: String): AuthEmailResult
    suspend fun updateEmail(email: String): AuthEmailResult
    suspend fun updatePassword(password: String): AuthEmailResult
    suspend fun sendEmailVerification(): AuthEmailResult
    suspend fun sendPasswordResetEmail(email: String): AuthEmailResult
}