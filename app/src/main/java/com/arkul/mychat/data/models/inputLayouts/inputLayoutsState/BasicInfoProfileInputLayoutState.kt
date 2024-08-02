package com.arkul.mychat.data.models.inputLayouts.inputLayoutsState

data class BasicInfoProfileInputLayoutState(
    val firstName: String = "",
    val username: String = "",
    val lastName: String = "",
    val birthday: String = "",
    val aboutMe: String = "",
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val usernameError: String? = null,
    val birthdayError: String? = null,
)