package com.arkul.mychat.utilities.validator

import com.arkul.mychat.utilities.validator.models.ValidationResult

class FieldValidate(
    private val fieldText: String,
) : Validator {

    override fun validate(): ValidationResult {
        if (fieldText.isBlank()) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "Field is can not be empty"
            )
        }

        return ValidationResult(true)
    }
}