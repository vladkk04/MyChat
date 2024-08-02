package com.arkul.mychat.data.models.inputLayouts.inputLayoutsEvents

sealed class EditProfileInputLayoutEvents {
    data class FullName(val fullName: String) : EditProfileInputLayoutEvents()
    data class NickName(val nickName: String) : EditProfileInputLayoutEvents()

}