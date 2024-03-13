package com.arkul.mychat.utilities

import android.app.AlertDialog
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.google.android.material.R.style as style
import com.google.android.material.dialog.MaterialAlertDialogBuilder

enum class TypeOfPermission {
    SMS,
    CAMERA
}

private fun getDescription(
    permission: TypeOfPermission,
    isPermanentlyDeclined: Boolean)
: String {
    return if (isPermanentlyDeclined) {
        when (permission) {
            TypeOfPermission.SMS -> "This app needs permission to send SMS. Please grant the permission manually through the app settings."
            TypeOfPermission.CAMERA -> "This app needs permission to access the camera. Please grant the permission manually through the app settings."
        }
    } else {
        when (permission) {
            TypeOfPermission.SMS -> "nigga"
            TypeOfPermission.CAMERA -> ""
        }
    }
}

fun Fragment.permissionDialog(
    permission: TypeOfPermission,
    isPermanentlyDeclined: Boolean,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit
) {
    MaterialAlertDialogBuilder(this.requireContext(), style.MaterialAlertDialog_Material3_Body_Text_CenterStacked)
        .setTitle("Permission required")
        .setMessage(getDescription(permission, isPermanentlyDeclined))
        .setNegativeButton(if(isPermanentlyDeclined) "Not now" else null) { dialog, _ ->
            dialog.dismiss()
        }
        .setPositiveButton(if(isPermanentlyDeclined) "Grant Permission" else "OK") { _, _ ->
            if (isPermanentlyDeclined) {
                onGoToAppSettingsClick()
            } else {
                onOkClick()
            }
        }.show().apply {
            val positiveButton = this.getButton(AlertDialog.BUTTON_POSITIVE)
            val negativeButton = this.getButton(AlertDialog.BUTTON_NEGATIVE)

            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            layoutParams.weight = 1f;
            //layoutParams.gravity = Gravity.CENTER

            positiveButton.layoutParams = layoutParams
            negativeButton.layoutParams = layoutParams
        }
}



