package com.arkul.mychat.data.network.firebase.services

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

abstract class AccountService() {
    val firebaseAuth
        get() = Firebase.auth

    val currentUser
        get() = firebaseAuth.currentUser

    val currentUserId: String
        get() = firebaseAuth.currentUser?.uid.orEmpty()

    val hasUser: Boolean
        get() = firebaseAuth.currentUser != null

    fun signOut() {
        if(hasUser) firebaseAuth.signOut()
    }

    suspend fun deleteAccount() {
        firebaseAuth.currentUser!!.delete().await()
    }
}