package com.arkul.mychat.data.network.firebase.repositories

import com.arkul.mychat.data.network.firebase.services.AccountService
import com.arkul.mychat.data.network.firebase.services.CredentialService
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.tasks.await
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