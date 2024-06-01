package com.arkul.mychat.ui.screens.createProfile.customizeAvatar

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkul.mychat.data.models.SelectAvatarModeEvents
import com.yalantis.ucrop.UCrop
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    val uCropSettings = UCrop.Options().apply {
        this.setShowCropGrid(false)
        this.setShowCropFrame(false)
        this.useSourceImageAspectRatio()
        this.setCircleDimmedLayer(true)
        this.setCompressionFormat(Bitmap.CompressFormat.JPEG)
        this.withMaxResultSize(2000, 1000)
        this.setCompressionQuality(80)
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