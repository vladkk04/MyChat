package com.arkul.mychat.ui.screens.createProfile.customizeAvatar

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.arkul.mychat.data.models.uiEvents.SelectAvatarModeEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class CustomizeAvatarViewModel @Inject constructor() : ViewModel() {

    private val _selectAvatarMode = MutableStateFlow<SelectAvatarModeEvents?>(null)
    val selectAvatarMode = _selectAvatarMode.asStateFlow()

    private val _originalAvatarGalleryUri = MutableStateFlow<Uri?>(null)
    val originalAvatarGalleryUri get() = _originalAvatarGalleryUri.asStateFlow()

    private val _originalAvatarCameraUri = MutableStateFlow<Uri?>(null)
    val originalAvatarCameraUri get() = _originalAvatarCameraUri.asStateFlow()

    private val _currentAvatarCameraBitmap = MutableStateFlow<Bitmap?>(null)
    val currentAvatarCameraBitmap get() = _currentAvatarCameraBitmap.asStateFlow()

    private val _currentAvatarGalleryBitmap = MutableStateFlow<Bitmap?>(null)
    val currentAvatarGalleryBitmap get() = _currentAvatarGalleryBitmap.asStateFlow()

    private val _currentAvatarDefault = MutableStateFlow<Drawable?>(null)
    val currentAvatarDefault get() = _currentAvatarDefault.asStateFlow()

    val currentAvatarBitmap = combine(_selectAvatarMode, _currentAvatarGalleryBitmap, _currentAvatarCameraBitmap) { mode, gallery, camera ->
        when(mode) {
            SelectAvatarModeEvents.Camera -> camera
            SelectAvatarModeEvents.Gallery -> gallery
            else -> return@combine null
        }
    }

    private val _backgroundColor = MutableStateFlow(-13025722)
    val backgroundColor get() = _backgroundColor.asStateFlow()

    fun selectAvatarMode(event: SelectAvatarModeEvents) {
        _selectAvatarMode.update { event }
    }

    fun changeBackground(color: Int) {
        _backgroundColor.update { color }
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

    fun setCurrentAvatarDefaultDrawable(
        value: Drawable,
    ) {
        _currentAvatarDefault.update { value }
    }
}