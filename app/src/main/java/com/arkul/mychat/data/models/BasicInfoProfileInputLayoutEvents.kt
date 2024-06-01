package com.arkul.mychat.data.models

import java.util.Locale

sealed class BasicInfoProfileInputLayoutEvents {
    data class FirstName(val firstName: String) : BasicInfoProfileInputLayoutEvents()
    data class LastName(val lastName: String) : BasicInfoProfileInputLayoutEvents()
    data class UserName(val userName: String) : BasicInfoProfileInputLayoutEvents()
    data class Birthdate(
        val value: Long,
        val pattern: String,
        val locale: Locale = Locale.getDefault()
    ) : BasicInfoProfileInputLayoutEvents()

    data class AboutMe(val aboutMe: String) : BasicInfoProfileInputLayoutEvents()
}