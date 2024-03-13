package com.arkul.mychat.utilities

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.fragment.app.Fragment

fun Fragment.openAppSettings() {
    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", requireActivity().packageName, null)
    ).also { startActivity(it) }
}