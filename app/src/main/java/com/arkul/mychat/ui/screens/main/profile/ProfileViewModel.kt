package com.arkul.mychat.ui.screens.main.profile

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import com.arkul.mychat.data.models.inputLayouts.inputLayoutsEvents.EditProfileInputLayoutEvents
import com.arkul.mychat.data.models.inputLayouts.inputLayoutsState.EditProfileInputLayoutState
import com.arkul.mychat.data.models.uiStates.ProfileUiState
import com.arkul.mychat.utilities.validator.EmptyFieldValidate
import com.arkul.mychat.utilities.validator.models.ValidationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    private val _inputLayoutState = MutableStateFlow(EditProfileInputLayoutState())
    val inputLayoutState = _inputLayoutState.asStateFlow()

    fun updateProfileInfo() {
        if (isInputValidation()) {
            updateFullName(_inputLayoutState.value.fullName)
            updateNickName(_inputLayoutState.value.nickName)
        }
    }

    fun inputLayoutEvent(event: EditProfileInputLayoutEvents) {
        when (event) {
            is EditProfileInputLayoutEvents.FullName -> _inputLayoutState.update { it.copy(fullName = event.fullName) }
            is EditProfileInputLayoutEvents.NickName -> _inputLayoutState.update { it.copy(nickName = event.nickName) }
        }
    }

    fun updateWallpaper(wallpaper: Bitmap) {
        _uiState.update {
            it.copy(
                wallpaper = wallpaper
            )
        }
    }

    fun updateAvatar(avatar: Bitmap) {
        _uiState.update {
            it.copy(
                avatar = avatar
            )
        }
    }

    private fun updateFullName(fullName: String) {
        _uiState.update {
            it.copy(
                fullName = fullName
            )
        }
    }

    private fun updateNickName(nickName: String) {
        _uiState.update {
            it.copy(
                nickName = nickName
            )
        }
    }
    private fun isInputValidation(): Boolean {
        val fullNameValidateResult = EmptyFieldValidate(_inputLayoutState.value.fullName).validate()
        val nickNameValidateResult = EmptyFieldValidate(_inputLayoutState.value.nickName).validate()

        return !ValidationResult.isValidate(
            fullNameValidateResult,
            nickNameValidateResult,
        ).apply {
            _inputLayoutState.update { state ->
                state.copy(
                    errorFullName = if (this) fullNameValidateResult.errorMessage else null,
                    errorNickName = if (this) nickNameValidateResult.errorMessage else null,
                )
            }
        }
    }
}