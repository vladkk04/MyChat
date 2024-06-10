package com.arkul.mychat.ui.screens.waitingVerify

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.arkul.mychat.data.models.WaitingVerifyUiState
import com.arkul.mychat.ui.navigation.BaseViewModel
import com.arkul.mychat.ui.navigation.models.NavigationEvent
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WaitingVerifyViewModel @Inject constructor() : BaseViewModel() {

    private val currentUser = Firebase.auth.currentUser

    private val _uiState = MutableStateFlow(
        WaitingVerifyUiState(
            userHasConfirmed = currentUser?.isEmailVerified ?: false
        )
    )

    val uiState get() = _uiState.asStateFlow()
    fun sendEmailVerification() {
        currentUser?.sendEmailVerification()?.addOnSuccessListener {
            startUpdateEmailVerify()
        }?.addOnFailureListener { e ->
            _uiState.update {
                it.copy(errorMessage = e.message)
            }
        }
    }

    private fun startUpdateEmailVerify() = viewModelScope.launch(Dispatchers.Default) {
        while (currentUser?.isEmailVerified == false) {
            currentUser.reload()
            delay(5000)
        }
    }.invokeOnCompletion {
        navigationToCreateProfile()
    }

    fun shouldResendConfirmation(value: Boolean = false) {
        _uiState.update {
            it.copy(
                shouldResendConfirmation = value
            )
        }
    }

    private fun navigationToCreateProfile() {
        navigateTo(NavigationEvent.OnNavigateTo(WaitingVerifyEmailDirections.actionWaitingVerifyEmailToCreateProfileFragment()))
    }
}