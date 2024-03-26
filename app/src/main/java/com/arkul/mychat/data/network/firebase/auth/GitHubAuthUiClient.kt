package com.arkul.mychat.data.network.firebase.auth

import android.app.Activity
import android.util.Log
import com.arkul.mychat.data.models.auth.CredentialResult
import com.google.firebase.auth.GithubAuthCredential
import com.google.firebase.auth.GithubAuthProvider
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


class GitHubAuthUiClient(
    private val activity: Activity
) {
    private val providerOAuthProviderGitHub
        get() = OAuthProvider.newBuilder("github.com").build()

    suspend fun getAuthCredential(): CredentialResult {
        return try {
            val authResult = Firebase.auth.startActivityForSignInWithProvider(
                activity,
                this.providerOAuthProviderGitHub
            ).await()

            CredentialResult(credential = authResult.credential)
        } catch (e: Exception) {
            CredentialResult(
                errorMessage = e.message
            )
        }
    }
}