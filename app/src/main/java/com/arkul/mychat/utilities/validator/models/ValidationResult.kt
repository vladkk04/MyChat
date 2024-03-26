package com.arkul.mychat.utilities.validator.models

data class ValidationResult(
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
) {
    companion object {
        fun isValidate(vararg validator: ValidationResult): Boolean = validator.any { !it.isSuccess }
    }
}




