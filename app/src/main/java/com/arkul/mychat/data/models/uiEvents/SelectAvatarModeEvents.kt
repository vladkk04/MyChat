package com.arkul.mychat.data.models.uiEvents

sealed class SelectAvatarModeEvents {
    data object Default : SelectAvatarModeEvents()
    data object Gallery : SelectAvatarModeEvents()
    data object Camera : SelectAvatarModeEvents()
}