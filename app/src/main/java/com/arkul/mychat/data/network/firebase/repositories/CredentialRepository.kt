package com.arkul.mychat.data.network.firebase.repositories

import com.arkul.mychat.data.models.auth.AuthCredentialProviderResult
import com.arkul.mychat.data.models.auth.UserData
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

    override suspend fun signInWithCredential(credential: AuthCredential): AuthCredentialProviderResult {
        return try {
            firebaseAuth.signInWithCredential(credential).await().let {
                AuthCredentialProviderResult(UserData(
                    it.additionalUserInfo?.username,
                    it.user?.displayName,
                    it.additionalUserInfo?.isNewUser
                ))
            }
        } catch (e: Exception) {
            if (e is FirebaseAuthInvalidCredentialsException) {
                AuthCredentialProviderResult(errorMessage = "Account doesn't exist or password is incorrect")
            } else {
                AuthCredentialProviderResult(errorMessage = e.message)
            }
        }
    }

    override suspend fun linkWithCredential(credential: AuthCredential): AuthCredentialProviderResult {
        return try {
            firebaseAuth.currentUser!!.linkWithCredential(credential).await()
            AuthCredentialProviderResult()
        } catch (e: Exception) {
            AuthCredentialProviderResult(errorMessage = e.message)
        }
    }

    override suspend fun unlinkCredential(credential: String): AuthCredentialProviderResult {
        return try {
            firebaseAuth.currentUser!!.unlink(credential).await()
            AuthCredentialProviderResult()
        } catch (e: Exception) {
            AuthCredentialProviderResult(errorMessage = e.message)
        }
    }
}