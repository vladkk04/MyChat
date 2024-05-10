package com.arkul.mychat.utilities.dialogs

import android.app.AlertDialog
import android.view.Gravity
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.arkul.mychat.R
import com.arkul.mychat.utilities.permission.AppPermissionDialogs
import com.google.android.material.R.style as style
import com.google.android.material.dialog.MaterialAlertDialogBuilder

private fun getDescription(permission: AppPermissionDialogs, isPermanentlyDeclined: Boolean): String? {
    return when (isPermanentlyDeclined) {
        true -> permission.permanentlyDeclinedText
        false -> permission.declinedText
    }
}

fun Fragment.permissionDialog(
    permission: AppPermissionDialogs,
    isPermanentlyDeclined: Boolean,
    onDismissClick: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit
) {
    MaterialAlertDialogBuilder(requireContext())
        .setTitle("Permission required")
        .setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_camera))
        .setMessage(getDescription(permission, isPermanentlyDeclined))
        .setOnDismissListener {
            onDismissClick()
        }
        .setNegativeButton(if(isPermanentlyDeclined) "Not now" else null) { dialog, _ ->
            dialog.dismiss()
        }
        .setPositiveButton(if(isPermanentlyDeclined) "Grant permission" else "OK") { _, _ ->
            if (isPermanentlyDeclined) {
                onGoToAppSettingsClick()
            } else {
                onOkClick()
                onDismissClick()
            }

        }.show()
}



