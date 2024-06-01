package com.arkul.mychat.data.network.firebase.auth

import android.app.Activity
import com.arkul.mychat.data.models.auth.AuthCredentialProviderResult
import com.arkul.mychat.data.models.auth.UserData
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


class GitHubAuthUiClient(
    private val activity: Activity
) {
    private val providerOAuthProviderGitHub
        get() = OAuthProvider.newBuilder("github.com").build()

    suspend fun getAuthCredential(): AuthCredentialProviderResult {
        return try {
            val result = Firebase.auth.startActivityForSignInWithProvider(
                activity,
                this.providerOAuthProviderGitHub
            ).await()

            AuthCredentialProviderResult(
                UserData(
                    result.additionalUserInfo?.username,
                    result.user?.displayName,
                    result.additionalUserInfo?.isNewUser
                )
            )

        } catch (e: Exception) {
            AuthCredentialProviderResult(errorMessage = e.message)
        }
    }
}