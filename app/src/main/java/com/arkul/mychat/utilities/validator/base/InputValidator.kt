package com.arkul.mychat.utilities.validator.base

import com.arkul.mychat.utilities.validator.EmailValidate
import com.arkul.mychat.utilities.validator.PasswordRepeatedValidate
import com.arkul.mychat.utilities.validator.PasswordValidate
class InputValidator {
    var emailError: String? = ""
        private set
    var passwordError: String? = ""
        private set
    var passwordRepeatedError: String? = ""
        private set
    fun checkValidation(vararg validators: Validator): Boolean {
        return validators.any { !it.validate().isSuccess }.apply {
            if(this) {
                for (validator in validators) {
                    when (validator) {
                        is EmailValidate -> {
                            emailError = validator.validate().errorMessage
                        }
                        is PasswordValidate -> {
                            passwordError = validator.validate().errorMessage
                        }
                        is PasswordRepeatedValidate -> {
                            passwordRepeatedError = validator.validate().errorMessage
                        }
                    }
                }
            }
        }
    }
}