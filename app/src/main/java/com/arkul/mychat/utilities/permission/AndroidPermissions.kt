package com.arkul.mychat.utilities.permission

import android.Manifest

enum class AndroidPermissions(val permission: String) {
    CAMERA(Manifest.permission.CAMERA),
    LOCATION(Manifest.permission.ACCESS_COARSE_LOCATION)

}