package com.arkul.mychat.ui.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arkul.mychat.ui.navigation.models.NavigationEvent

abstract class BaseViewModel : ViewModel() {

    private val _navigationEvent: MutableLiveData<Event<NavigationEvent>> = MutableLiveData()
    val navigationEvent get() = _navigationEvent
    protected fun navigateTo(navigationEvent: NavigationEvent) {
        _navigationEvent.postValue(Event(navigationEvent))
    }

    protected fun navigateBack() {
        _navigationEvent.postValue(Event(NavigationEvent.OnNavigateBack))
    }
}