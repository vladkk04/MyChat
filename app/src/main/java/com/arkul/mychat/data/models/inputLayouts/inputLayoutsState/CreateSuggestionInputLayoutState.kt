package com.arkul.mychat.data.models.inputLayouts.inputLayoutsState

data class CreateSuggestionInputLayoutState(
    val topic: String = "",
    val description: String = "",
    val topicError: String? = null,
    val descriptionError: String? = null
)
