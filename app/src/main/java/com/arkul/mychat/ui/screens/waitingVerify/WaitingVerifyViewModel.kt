package com.arkul.mychat.ui.screens.waitingVerify

import androidx.lifecycle.viewModelScope
import com.arkul.mychat.data.models.WaitingVerifyUiState
import com.arkul.mychat.ui.navigation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WaitingVerifyViewModel @Inject constructor() : BaseViewModel() {
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

    fun shouldResendConfirmation(value: Boolean = false) {
        if(!uiState.value.shouldResendConfirmation) return
        _uiState.update {
            it.copy(
                shouldResendConfirmation = value
            )
        }
    }
}