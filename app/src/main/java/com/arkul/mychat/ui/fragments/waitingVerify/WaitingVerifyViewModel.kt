package com.arkul.mychat.ui.fragments.waitingVerify

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkul.mychat.data.models.WaitingVerifyUiState
import com.arkul.mychat.ui.navigation.BaseViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WaitingVerifyViewModel @Inject constructor(): BaseViewModel() {
    private val _uiState = MutableStateFlow(WaitingVerifyUiState())
    val uiState get() = _uiState.asStateFlow()

    init {
        //startUpdateEmailVerify()
    }

    private fun startUpdateEmailVerify() = viewModelScope.launch(Dispatchers.Default) {
       /* while (!Firebase.auth.currentUser?.isEmailVerified!!) {
            Log.d("d", "hello")
            Firebase.auth.currentUser!!.reload().addOnFailureListener { e ->
                _uiState.update {
                    it.copy(
                        errorMessage = e.message
                    )
                }
            }
            delay(3000)
        }
        this.cancel()*/
    }

    fun setResendConfirmation(value: Boolean) {
        _uiState.update {
            it.copy(
                isResendConfirmation = value
            )
        }
    }
}