package com.arkul.mychat.data.network.firebase

import android.app.Activity
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GithubAuthCredential
import com.google.firebase.auth.GoogleAuthCredential


sealed class AuthMethod {
    data object Email : AuthMethod()
    data class GitHub(val activity: Activity) : AuthMethod()
    data class Google(val googleIdTokenCredential: GoogleIdTokenCredential): AuthMethod()
}