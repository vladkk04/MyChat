package com.arkul.mychat.data.network.firebase.repositories

import com.arkul.mychat.data.models.AuthEmailResult
import com.arkul.mychat.data.network.firebase.services.AccountService
import com.arkul.mychat.data.network.firebase.services.EmailService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class EmailRepository @Inject constructor() : AccountService(), EmailService {
    override val accountService: AccountService
        get() = this

    override suspend fun createUserWithEmailAndPassword(
        email: String, password: String
    ): AuthEmailResult {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await().let {
                AuthEmailResult(
                    isSuccess = true,
                    isVerifyEmail = it.user?.isEmailVerified
                )
            }
        } catch (e: Exception) {
            AuthEmailResult(errorMessage = e.message)
        }

    }

    override suspend fun updateEmail(email: String): AuthEmailResult {
        return try {
            firebaseAuth.currentUser?.verifyBeforeUpdateEmail(email)?.await()
            AuthEmailResult(isSuccess = true)
        } catch (e: Exception) {
            AuthEmailResult(errorMessage = e.message)
        }
    }

    override suspend fun updatePassword(password: String): AuthEmailResult {
        return try {
            firebaseAuth.currentUser?.updatePassword(password)?.await()
            AuthEmailResult(isSuccess = true)
        } catch (e: Exception) {
            AuthEmailResult(errorMessage = e.message)
        }
    }

    override suspend fun sendEmailVerification(): AuthEmailResult {
        return try {
            firebaseAuth.currentUser?.sendEmailVerification()?.await()
            AuthEmailResult(isSuccess = true)
        } catch (e: Exception) {
            AuthEmailResult(errorMessage = e.message)
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): AuthEmailResult {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            AuthEmailResult(isSuccess = true)
        } catch (e: Exception) {
            AuthEmailResult(errorMessage = e.message)
        }
    }
}