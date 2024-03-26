package com.arkul.mychat.data.network.firebase.repositories

import com.arkul.mychat.data.models.auth.AuthCredentialResult
import com.arkul.mychat.data.network.firebase.services.AccountService
import com.arkul.mychat.data.network.firebase.services.CredentialService
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CredentialRepository @Inject constructor(
) : AccountService(), CredentialService {
    override val accountService: AccountService
        get() = this

    override suspend fun signInWithCredential(credential: AuthCredential): AuthCredentialResult {
        return try {
            val user = firebaseAuth.signInWithCredential(credential).await()
            AuthCredentialResult()
        } catch (e: Exception) {
            if (e is FirebaseAuthInvalidCredentialsException) {
                AuthCredentialResult(errorMessage = "Account doesn't exist or password is incorrect")
            } else {
                AuthCredentialResult(errorMessage = e.message)
            }
        }
    }

    override suspend fun linkWithCredential(credential: AuthCredential): AuthCredentialResult {
        return try {
            firebaseAuth.currentUser!!.linkWithCredential(credential).await()
            AuthCredentialResult()
        } catch (e: Exception) {
            AuthCredentialResult(errorMessage = e.message)
        }
    }

    override suspend fun unlinkCredential(credential: String): AuthCredentialResult {
        return try {
            firebaseAuth.currentUser!!.unlink(credential).await()
            AuthCredentialResult()
        } catch (e: Exception) {
            AuthCredentialResult(errorMessage = e.message)
        }
    }
}