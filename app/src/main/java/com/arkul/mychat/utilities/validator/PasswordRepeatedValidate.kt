package com.arkul.mychat.utilities.validator

import com.arkul.mychat.utilities.validator.models.ValidationResult

class PasswordRepeatedValidate(
    private val password: String,
    private val passwordRepeated: String
): Validator {
    override fun validate(): ValidationResult {
        if (password != passwordRepeated) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "The passwords don't match"
            )
        }
        return ValidationResult(true)
    }
}