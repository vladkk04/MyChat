package com.arkul.mychat.ui.screens.createProfile.initBasicInfoProfile

import androidx.lifecycle.ViewModel
import com.arkul.mychat.data.models.BasicInfoProfileInputLayoutEvents
import com.arkul.mychat.data.models.BasicInfoProfileInputLayoutState
import com.arkul.mychat.utilities.validator.FieldValidate
import com.arkul.mychat.utilities.validator.models.ValidationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BasicInfoProfileViewModel : ViewModel() {

    private val _stateTextLayout = MutableStateFlow(BasicInfoProfileInputLayoutState())
    val stateTextLayout get() = _stateTextLayout.asStateFlow()

    fun onEvent(event: BasicInfoProfileInputLayoutEvents) {
        when (event) {
            is BasicInfoProfileInputLayoutEvents.FirstName -> {
                _stateTextLayout.update {
                    it.copy(firstName = event.firstName)
                }
            }

            is BasicInfoProfileInputLayoutEvents.LastName -> {
                _stateTextLayout.update { state ->
                    state.copy(lastName = event.lastName)
                }
            }

            is BasicInfoProfileInputLayoutEvents.UserName -> {
                _stateTextLayout.update { state ->
                    state.copy(username = event.userName)
                }
            }

            is BasicInfoProfileInputLayoutEvents.Birthdate -> {
                _stateTextLayout.update { state ->
                    state.copy(
                        birthday = getFormattedBirthday(
                            value = event.value,
                            pattern = event.pattern,
                            locale = event.locale
                        )
                    )
                }
            }
            is BasicInfoProfileInputLayoutEvents.AboutMe -> {
                _stateTextLayout.update { state ->
                    state.copy(aboutMe = event.aboutMe)
                }
            }

        }
    }

    fun isValidatedInputs(): Boolean {
        val firstNameValidateResult = FieldValidate(_stateTextLayout.value.firstName).validate()
        val lastNameValidateResult = FieldValidate(_stateTextLayout.value.lastName).validate()
        val userNameValidateResult = FieldValidate(_stateTextLayout.value.username).validate()

        return !ValidationResult.isValidate(
            firstNameValidateResult,
            lastNameValidateResult,
            userNameValidateResult,
        ).apply {
            _stateTextLayout.update { state ->
                state.copy(
                    firstNameError = if (this) firstNameValidateResult.errorMessage else null,
                    lastNameError = if (this) lastNameValidateResult.errorMessage else null,
                    usernameError = if (this) userNameValidateResult.errorMessage else null
                )
            }
        }
    }
    private fun getFormattedBirthday(
        value: Long,
        pattern: String,
        locale: Locale
    ): String {
        val formatter = SimpleDateFormat(pattern, locale)
        val newDate = Date(value)
        return formatter.format(newDate)
    }
}