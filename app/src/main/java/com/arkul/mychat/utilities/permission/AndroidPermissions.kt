package com.arkul.mychat.utilities.permission

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

enum class AndroidPermissions(val permission: String) {
    CAMERA(Manifest.permission.CAMERA),
    LOCATION(Manifest.permission.ACCESS_COARSE_LOCATION),

    @RequiresApi(Build.VERSION_CODES.S_V2)
    READ_EXTERNAL_STORAGE(Manifest.permission.READ_EXTERNAL_STORAGE),

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    READ_MEDIA_IMAGES(Manifest.permission.READ_MEDIA_IMAGES),

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    READ_MEDIA_VISUAL_USER_SELECTED(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED);

    companion object {
        fun getMediaPermission(): Array<AndroidPermissions> {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                arrayOf(READ_MEDIA_VISUAL_USER_SELECTED, READ_MEDIA_IMAGES)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arrayOf(READ_MEDIA_IMAGES)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2) {
                arrayOf(READ_EXTERNAL_STORAGE)
            } else {
                throw IllegalArgumentException("No media permission")
            }
        }
    }
}