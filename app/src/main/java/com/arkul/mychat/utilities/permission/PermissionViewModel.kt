package com.arkul.mychat.utilities.permission

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

    private val _visiblePermissionDialogQueue = MutableStateFlow(emptyList<String>())
    val dialogQueue get() = _visiblePermissionDialogQueue.asStateFlow()

    fun dismissDialog() {
        _visiblePermissionDialogQueue.update { it.drop(1) }
    }

    fun launchSinglePermission(androidPermissions: AndroidPermissions) {
        activityResultRegistry.register(
            "SINGLE_LAUNCH_PERMISSION",
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            onPermissionResult(androidPermissions.permission, isGranted)
        }.launch(androidPermissions.permission)
    }

    fun launchMultiplyPermission(vararg androidPermissions: AndroidPermissions) {
        activityResultRegistry.register(
            "MULTIPLY_LAUNCH_PERMISSIONS",
            ActivityResultContracts.RequestMultiplePermissions()
        ) { perms ->
            androidPermissions.forEach { permission ->
                onPermissionResult(
                    permission = permission.permission,
                    isGranted = if (perms.keys.contains(permission.permission)) {
                        perms[permission.permission] == true
                    } else true
                )
            }
        }.launch(androidPermissions.map { it.permission }.toTypedArray())
    }

    private fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if (!isGranted && !_visiblePermissionDialogQueue.value.contains(permission)) {
            _visiblePermissionDialogQueue.value = listOf(permission).reversed()
        }
    }
}