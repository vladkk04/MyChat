package com.arkul.mychat.data.models.uiEvents

import com.arkul.mychat.data.models.Message

sealed class ChatEvents {
    data object OpenGallery : ChatEvents()
    data object OpenEmojiBottomPanel : ChatEvents()
    data class SendMessage(val message: Message) : ChatEvents()
}