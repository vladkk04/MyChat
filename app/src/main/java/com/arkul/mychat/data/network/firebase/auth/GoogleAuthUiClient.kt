package com.arkul.mychat.data.network.firebase.auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.arkul.mychat.BuildConfig
import com.arkul.mychat.data.models.auth.AuthCredentialNotProviderResult
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.GoogleAuthProvider

class GoogleAuthUiClient(
    val context: Context
) {
    private val credentialManager = CredentialManager.create(context)

    private val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(BuildConfig.AUTH_WEB_CLIENT_ID_GOOGLE)
        .build()

    private val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    suspend fun getAuthCredential(): AuthCredentialNotProviderResult {
        try {
            credentialManager.getCredential(
                context = context,
                request = request
            ).apply {
                val googleIdToken = GoogleIdTokenCredential.createFrom(this.credential.data)
                val credential =  GoogleAuthProvider.getCredential(googleIdToken.idToken, null)
                return AuthCredentialNotProviderResult(credential = credential)
            }
        } catch (e: Exception) {
            return AuthCredentialNotProviderResult(errorMessage = e.message)
        }
    }

}