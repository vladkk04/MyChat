package com.arkul.mychat.ui.screens.bottomSheets

import androidx.lifecycle.ViewModel
import com.arkul.mychat.data.models.Suggestion
import com.arkul.mychat.data.models.inputLayouts.inputLayoutsEvents.CreateSuggestionInputLayoutEvents
import com.arkul.mychat.data.models.inputLayouts.inputLayoutsState.CreateSuggestionInputLayoutState
import com.arkul.mychat.utilities.validator.EmptyFieldValidate
import com.arkul.mychat.utilities.validator.models.ValidationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SuggestionBottomSheetViewModel: ViewModel() {

    private val _inputLayoutState = MutableStateFlow(CreateSuggestionInputLayoutState())
    val inputLayoutState = _inputLayoutState.asStateFlow()

    fun createSuggestion(suggestion: Suggestion): Boolean {
        return if (isValidatedInputs()) {
            true
        } else {
            false
        }
    }

    fun onEventInputLayout(event: CreateSuggestionInputLayoutEvents) {
        when (event) {
            is CreateSuggestionInputLayoutEvents.TopicChanged -> {
                _inputLayoutState.update { it.copy(topic = event.topic) }
            }
            is CreateSuggestionInputLayoutEvents.DescriptionChanged -> {
                _inputLayoutState.update { it.copy(description = event.description) }
            }
        }
    }

    private fun isValidatedInputs(): Boolean {
        val topicValidateResult = EmptyFieldValidate(_inputLayoutState.value.topic, 20).validate()
        val descriptionValidateResult = EmptyFieldValidate(_inputLayoutState.value.description, 200).validate()

        return !ValidationResult.isValidate(
            topicValidateResult,
            descriptionValidateResult,
        ).apply {
            _inputLayoutState.update { state ->
                state.copy(
                    topicError = if (this) topicValidateResult.errorMessage else null,
                    descriptionError = if (this) descriptionValidateResult.errorMessage else null,
                )
            }
        }
    }

}