package com.arkul.mychat.data.network.firebase.services

interface EmailService {
    val accountService: AccountService
    suspend fun signInWithEmail(email: String, password: String)
    suspend fun signUpWithEmail(email: String, password: String)
}