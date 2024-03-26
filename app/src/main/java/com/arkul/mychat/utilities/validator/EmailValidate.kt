package com.arkul.mychat.utilities.validator

import android.util.Patterns
import com.arkul.mychat.utilities.validator.models.ValidationResult

class EmailValidate(
    private val email: String
): Validator {
    override fun validate(): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "The email can't be blank"
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "That's not a valid email"
            )
        }

        return ValidationResult(true)
    }

}