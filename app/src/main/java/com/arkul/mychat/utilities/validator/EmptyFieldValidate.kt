package com.arkul.mychat.utilities.validator

import com.arkul.mychat.utilities.validator.models.ValidationResult

class EmptyFieldValidate(
    private val fieldText: String,
    private val maxFieldLength: Int? = null
) : Validator {

    override fun validate(): ValidationResult {
        if (fieldText.isBlank()) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "Field is can not be empty"
            )
        }

        if (maxFieldLength != null && fieldText.length > maxFieldLength) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "Field is too long"
            )
        }

        return ValidationResult(true)
    }
}