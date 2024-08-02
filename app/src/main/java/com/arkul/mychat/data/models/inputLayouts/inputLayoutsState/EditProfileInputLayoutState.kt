package com.arkul.mychat.data.models.inputLayouts.inputLayoutsState

data class EditProfileInputLayoutState(
    val fullName: String = "",
    val nickName: String = "",
    val errorFullName: String? = null,
    val errorNickName: String? = null
)