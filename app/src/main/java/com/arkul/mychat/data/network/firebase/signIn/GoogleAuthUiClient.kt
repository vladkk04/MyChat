package com.arkul.mychat.data.network.firebase.signIn

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
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
            Log.d("d", "start cred")
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