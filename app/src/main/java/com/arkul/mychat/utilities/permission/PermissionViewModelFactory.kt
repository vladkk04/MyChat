package com.arkul.mychat.utilities.permission

import androidx.activity.result.ActivityResultRegistry
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import dagger.assisted.AssistedFactory
import dagger.hilt.android.lifecycle.withCreationCallback


@AssistedFactory
interface PermissionViewModelFactory {
    fun create(activityResultRegistry: ActivityResultRegistry): PermissionViewModel
}
inline fun <reified VM : ViewModel> Fragment.permissionViewModel() = viewModels<VM>(extrasProducer = {
    defaultViewModelCreationExtras.withCreationCallback<PermissionViewModelFactory> { factory ->
        factory.create(requireActivity().activityResultRegistry)
    }
})
