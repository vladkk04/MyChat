package com.arkul.mychat.utilities.permission

import android.Manifest


enum class AppPermissionDialogs(
    val permanentlyDeclinedText: String? = null,
    val declinedText: String? = null
) {
    CAMERA(
        "This app needs permission to access the camera. Please grant the permission manually through the app settings.",
        "Hello"
    )
}