package com.arkul.mychat.ui.screens.createProfile

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkul.mychat.data.models.ViewPagerNavigationEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SharedProfileViewModel : ViewModel() {

    private var isResumedFragment = false

    private var isValidation: (() -> Boolean)? = null

    private val _viewPagerNavigationEvent = MutableSharedFlow<ViewPagerNavigationEvent>()

    private val _args = MutableStateFlow<CreateProfileFragmentArgs?>(null)
    val args = _args.asStateFlow()

    fun setValidator(block: (() -> Boolean)? = null) {
        isResumedFragment = true
        isValidation = block
    }

    val viewPagerNavigationEvent
        get() = _viewPagerNavigationEvent.shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun onNavigatePage(
        event: ViewPagerNavigationEvent,
    ) = viewModelScope.launch {
        when (event) {
            ViewPagerNavigationEvent.OnBackPage -> _viewPagerNavigationEvent.emit(event)
            ViewPagerNavigationEvent.OnNextPage -> {
                if (isResumedFragment) {
                    isValidation?.invoke()?.let {
                        if (it) {
                            isResumedFragment = false
                            _viewPagerNavigationEvent.emit(event)
                        }
                    }
                }
            }
        }
    }
}