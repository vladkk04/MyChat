package com.arkul.mychat.data.models.uiStates

import android.graphics.Bitmap

data class ProfileUiState(
    val fullName: String = "",
    val nickName: String = "",
    val wallpaper: Bitmap? = null,
    val avatar: Bitmap? = null,
    val birthday: String = "",
    val description: String = ""
)
