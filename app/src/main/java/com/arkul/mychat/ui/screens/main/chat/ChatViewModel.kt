package com.arkul.mychat.ui.screens.main.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkul.mychat.data.models.uiEvents.ChatEvents
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ChatViewModel: ViewModel() {

    private val _chatEvents = MutableSharedFlow<ChatEvents>()
    val chatEvents = _chatEvents.asSharedFlow()

    fun setChatEvents(chatEvents: ChatEvents) = viewModelScope.launch {
        _chatEvents.emit(chatEvents)
    }

}