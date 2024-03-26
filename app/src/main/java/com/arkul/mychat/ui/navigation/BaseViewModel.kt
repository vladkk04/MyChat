package com.arkul.mychat.ui.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {

    private val _navigationEvent: MutableLiveData<NavigationEvent> = MutableLiveData()
    val navigationEvent get() = _navigationEvent
    protected fun navigate(navigationEvent: NavigationEvent) {
        _navigationEvent.postValue(navigationEvent)
    }
}