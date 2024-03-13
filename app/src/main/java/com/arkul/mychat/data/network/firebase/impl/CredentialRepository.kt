package com.arkul.mychat.data.network.firebase.impl

import com.arkul.mychat.data.network.firebase.services.AccountService
import com.arkul.mychat.data.network.firebase.services.CredentialService
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CredentialRepository @Inject constructor(
): AccountService(), CredentialService {
    override val accountService: AccountService
        get() = this

    override suspend fun signInWithCredential(credential: AuthCredential)  {
        firebaseAuth.signInWithCredential(credential).await()
    }

    override suspend fun linkWithCredential(credential: AuthCredential) {
        firebaseAuth.currentUser!!.linkWithCredential(credential).await()
    }

    override suspend fun unlinkCredential(credential: String) {
        firebaseAuth.currentUser!!.unlink(credential).await()
    }
}