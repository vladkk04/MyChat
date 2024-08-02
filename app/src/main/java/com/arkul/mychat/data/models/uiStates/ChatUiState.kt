package com.arkul.mychat.data.models.uiStates

import com.arkul.mychat.data.models.Message

data class ChatUiState(
    val messages: List<Message> = emptyList(),
)