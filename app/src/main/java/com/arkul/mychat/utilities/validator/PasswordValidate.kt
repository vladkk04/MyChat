package com.arkul.mychat.utilities.validator

import com.arkul.mychat.utilities.validator.base.Validator
import com.arkul.mychat.utilities.validator.models.ValidationResult

class PasswordValidate(
    private val password: String
): Validator {
    override fun validate(): ValidationResult {
        val containsLettersAndDigit = password.any{ it.isDigit() } && password.any{ it.isLetter() }

        if (password.length < 8) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "The password сan not be less than 8 characters"
            )
        }
        if (!containsLettersAndDigit) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "Password requires at least one digit and one letter"
            )
        }

        return ValidationResult(true)
    }
}