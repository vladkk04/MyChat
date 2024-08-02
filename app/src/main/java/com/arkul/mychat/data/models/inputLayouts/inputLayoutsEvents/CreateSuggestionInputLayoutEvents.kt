package com.arkul.mychat.data.models.inputLayouts.inputLayoutsEvents

sealed class CreateSuggestionInputLayoutEvents {
    data class TopicChanged(val topic: String): CreateSuggestionInputLayoutEvents()
    data class DescriptionChanged(val description: String): CreateSuggestionInputLayoutEvents()
}