package com.arkul.mychat.data.network.firebase.services

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

abstract class AccountService() {

    protected val providerOAuthProviderGitHubBuilder
        get() = OAuthProvider.newBuilder("github.com")

    val providerOAuthProviderGitHub
        get() = OAuthProvider.newBuilder("github.com").build()

    val firebaseAuth
        get() = Firebase.auth

    val currentUserId: String
        get() = Firebase.auth.currentUser?.uid.orEmpty()

    val hasUser: Boolean
        get() = Firebase.auth.currentUser != null

    val pendingResult: Task<AuthResult>?
        get() = Firebase.auth.pendingAuthResult

    fun signOut() {
        Firebase.auth.signOut()
    }

    suspend fun deleteAccount() {
        Firebase.auth.currentUser!!.delete().await()
    }
}