package com.arkul.mychat.ui.screens.createProfile.customizeAvatar

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yalantis.ucrop.UCrop
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CustomizeAvatarViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

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

    fun changeBackground(color: Int) {
        _backgroundColor.value = color
    }
}