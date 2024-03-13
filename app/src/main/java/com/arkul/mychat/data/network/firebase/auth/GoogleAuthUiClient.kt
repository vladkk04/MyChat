package com.arkul.mychat.data.network.firebase.auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.arkul.mychat.BuildConfig
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

class GoogleAuthUiClient(
    val context: Context
) {
    private val credentialManager = CredentialManager.create(context)

    private val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(true)
        .setServerClientId(BuildConfig.AUTH_WEB_CLIENT_ID_GOOGLE)
        .build()

    private val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    suspend fun getCredential(): GoogleIdTokenCredential {
        try {
            credentialManager.getCredential(
                context = context,
                request = request
            ).apply {
                return GoogleIdTokenCredential.createFrom(this.credential.data)
            }
        } catch (e: GetCredentialException) {
            throw e
        }
    }
}