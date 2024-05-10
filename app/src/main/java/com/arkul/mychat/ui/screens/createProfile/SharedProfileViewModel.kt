package com.arkul.mychat.ui.screens.createProfile

import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.update
import java.util.Date
import java.util.Locale

class SharedProfileViewModel : ViewModel() {

    private val _isUserInputEnabledViewPager = MutableStateFlow(true)
    val isUserInputEnabledViewPager get() = _isUserInputEnabledViewPager.asStateFlow()

    private val _originalAvatarGalleryUri = MutableStateFlow<Uri?>(null)
    val originalAvatarGalleryUri get() = _originalAvatarGalleryUri.asStateFlow()

    private val _originalAvatarCameraUri = MutableStateFlow<Uri?>(null)
    val originalAvatarCameraUri get() = _originalAvatarCameraUri.asStateFlow()

    private val _currentAvatarCameraBitmap = MutableStateFlow<Bitmap?>(null)
    val currentAvatarCameraBitmap get() = _currentAvatarCameraBitmap.asStateFlow()

    private val _currentAvatarGalleryBitmap = MutableStateFlow<Bitmap?>(null)
    val currentAvatarGalleryBitmap get() = _currentAvatarGalleryBitmap.asStateFlow()

    val currentAvatarUri = merge(_currentAvatarCameraBitmap, _currentAvatarGalleryBitmap)

    private val _backgroundColor = MutableStateFlow(0)
    val backgroundColor get() = _backgroundColor.asStateFlow()

    private val _firstName = MutableStateFlow("")
    private val _lastName = MutableStateFlow("")

    val fullName =
        combine(_firstName.asStateFlow(), _lastName.asStateFlow()) { firstName, lastname ->
            "$firstName $lastname"
        }

    private val _userName = MutableStateFlow("")
    val userName get() = _userName.asStateFlow()

    private val _birthdayFormatted = MutableStateFlow("")
    val birthdayFormatted get() = _birthdayFormatted.asStateFlow()

    private val _aboutMe = MutableStateFlow("")
    val aboutMe get() = _aboutMe.asStateFlow()

    fun setBackgroundColor(value: Int) {
        _backgroundColor.update { value }
    }

    fun setFirstName(value: String) {
        _firstName.update { value.trim() }
    }

    fun setLastName(value: String) {
        _lastName.update { value.trim() }
    }

    fun setUserName(value: String) {
        _userName.update { value.trim() }
    }

    fun setAboutMe(value: String) {
        _aboutMe.update { value.trim() }
    }

    fun changeUserInputEnabledViewPagerState(value: Boolean) {
        _isUserInputEnabledViewPager.update { value }
    }

    fun saveOriginalAvatarGalleryUri(
        value: Uri?,
    ) {
        _originalAvatarGalleryUri.update { value }
    }

    fun saveOriginalAvatarCameraUri(
        value: Uri?,
    ) {
        _originalAvatarCameraUri.update { value }
    }

    fun setCurrentAvatarGalleryBitmap(
        value: Bitmap?,
    ) {
        _currentAvatarGalleryBitmap.update { value }
    }

    fun setCurrentAvatarCameraBitmap(
        value: Bitmap?,
    ) {
        _currentAvatarCameraBitmap.update { value }
    }

    fun setBirthday(
        value: Long,
        pattern: String,
        locale: Locale = Locale.getDefault()
    ) {
        val formatter = SimpleDateFormat(pattern, locale)
        val newDate = Date(value)
        val formattedDate = formatter.format(newDate)

        _birthdayFormatted.value = formattedDate
    }
}