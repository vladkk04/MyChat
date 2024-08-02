package com.arkul.mychat.utilities.permission

import android.util.Log
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


@HiltViewModel(assistedFactory = PermissionViewModelFactory::class)
class PermissionViewModel @AssistedInject constructor(
    @Assisted private val activityResultRegistry: ActivityResultRegistry
) : ViewModel() {

    private val _visiblePermissionDialogQueue = MutableStateFlow(emptyList<AndroidPermissions>())
    val dialogQueue get() = _visiblePermissionDialogQueue.asStateFlow()

    fun dismissDialog() {
        _visiblePermissionDialogQueue.update { it.drop(1) }
    }

    fun launchSinglePermission(androidPermissions: AndroidPermissions) {
        activityResultRegistry.register(
            "SINGLE_LAUNCH_PERMISSION",
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            onPermissionResult(androidPermissions, isGranted)
        }.launch(androidPermissions.permission)
    }

    fun launchMultiplyPermission(
        androidPermissions: Array<AndroidPermissions>,
        onPermissionResult: ((AndroidPermissions, Boolean) -> Unit)? = null,
        onPermissionCallback: (() -> Unit)? = null
    ) {
        activityResultRegistry.register(
            "MULTIPLY_LAUNCH_PERMISSIONS",
            ActivityResultContracts.RequestMultiplePermissions()
        ) { perms ->
            androidPermissions.forEach { permission ->
                val isGranted = if (permission.permission == "android.permission.READ_MEDIA_IMAGES") {
                    perms.containsKey(permission.permission) || perms.containsKey("android.permission.READ_MEDIA_VISUAL_USER_SELECTED")
                } else {
                    perms[permission.permission] == true
                }
                onPermissionResult(
                    permission = permission,
                    isGranted = isGranted
                )
                onPermissionResult?.invoke(permission, isGranted)
            }
            onPermissionCallback?.invoke()
        }.launch(androidPermissions.map { it.permission }.toTypedArray())
    }

    private fun onPermissionResult(
        permission: AndroidPermissions,
        isGranted: Boolean
    ) {
        if (!isGranted && !_visiblePermissionDialogQueue.value.contains(permission)) {
            _visiblePermissionDialogQueue.value = listOf(permission).reversed()
        }
    }
}