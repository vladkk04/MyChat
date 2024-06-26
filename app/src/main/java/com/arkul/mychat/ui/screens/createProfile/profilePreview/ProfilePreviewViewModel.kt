package com.arkul.mychat.ui.screens.createProfile.profilePreview

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfilePreviewViewModel: ViewModel() {

    private val _originalWallpaperUri = MutableStateFlow<Uri?>(null)
    val originalWallpaperUri = _originalWallpaperUri.asStateFlow()

    private val _croppedWallpaperBitmap = MutableStateFlow<Bitmap?>(null)
    val croppedWallpaperBitmap = _croppedWallpaperBitmap.asStateFlow()

    fun createProfile() {

    }

    fun setCroppedWallpaperBitmap(bitmap: Bitmap?) {
        _croppedWallpaperBitmap.value = bitmap
    }
    fun saveOriginalWallpaperUri(uri: Uri?) {
        _originalWallpaperUri.value = uri
    }
}