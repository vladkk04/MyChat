package com.arkul.mychat.utilities.permission

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

enum class AndroidPermissions(val permission: String) {
    CAMERA(Manifest.permission.CAMERA),
    LOCATION(Manifest.permission.ACCESS_COARSE_LOCATION),

    @RequiresApi(Build.VERSION_CODES.S_V2)
    STORAGE(Manifest.permission.READ_EXTERNAL_STORAGE),

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    MEDIA_LOCATION(Manifest.permission.READ_MEDIA_IMAGES),

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    GALLERY(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)
}