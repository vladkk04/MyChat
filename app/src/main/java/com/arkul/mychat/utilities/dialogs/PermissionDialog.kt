package com.arkul.mychat.utilities.dialogs

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.arkul.mychat.R
import com.arkul.mychat.utilities.app.openAppSettings
import com.arkul.mychat.utilities.permission.AppPermissionDialogs
import com.google.android.material.dialog.MaterialAlertDialogBuilder

private fun getDescription(permission: AppPermissionDialogs, isPermanentlyDeclined: Boolean): String? {
    return when (isPermanentlyDeclined) {
        true -> permission.permanentlyDeclinedText
        false -> permission.declinedText
    }
}

fun Fragment.permissionDialog(
    permissionDialog: AppPermissionDialogs,
    isPermanentlyDeclined: Boolean,
    onDismissClick: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit = ::openAppSettings
) {
    MaterialAlertDialogBuilder(requireContext())
        .setTitle("Permission required")
        .setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_camera))
        .setMessage(getDescription(permissionDialog, isPermanentlyDeclined))
        .setOnDismissListener {
            onDismissClick()
        }
        .setNegativeButton(if(isPermanentlyDeclined) "Not now" else null) { dialog, _ ->
            dialog.dismiss()
        }
        .setPositiveButton(if(isPermanentlyDeclined) "Go to settings" else "OK") { _, _ ->
            if (isPermanentlyDeclined) {
                onGoToAppSettingsClick()
            } else {
                onOkClick()
                onDismissClick()
            }

        }.show()
}



