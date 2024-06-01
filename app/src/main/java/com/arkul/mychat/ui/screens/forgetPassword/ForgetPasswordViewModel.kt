package com.arkul.mychat.ui.screens.forgetPassword

import com.arkul.mychat.ui.navigation.BaseViewModel
import com.arkul.mychat.ui.navigation.Event
import com.arkul.mychat.ui.navigation.models.NavigationEvent

class ForgetPasswordViewModel : BaseViewModel() {
    fun sendRestPassword() {
        navigateTo(NavigationEvent.OnNavigateTo(ForgetPasswordFragmentDirections.actionForgetPasswordFragmentToSuccessfullySentEmailFragment()))
    }
}