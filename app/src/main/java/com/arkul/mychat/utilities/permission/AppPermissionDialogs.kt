package com.arkul.mychat.utilities.permission

import android.Manifest


sealed class AppPermissionDialogs(
    open val permanentlyDeclinedText: String? = null,
    open val declinedText: String? = null
) {
    data class CAMERA(
        override val permanentlyDeclinedText: String? = "This app needs permission to access the gallery. Please grant the permission manually through the app settings.",
        override val declinedText: String? = "Go to Settings"
    ): AppPermissionDialogs(permanentlyDeclinedText, declinedText)

    data class GALLERY(
        override val permanentlyDeclinedText: String? = "This app needs permission to access the gallery. Please grant the permission manually through the app settings.",
        override val declinedText: String? = "Go to Settings",
        val isLimitedAccess: Boolean = false
    ): AppPermissionDialogs(permanentlyDeclinedText, declinedText)
}
