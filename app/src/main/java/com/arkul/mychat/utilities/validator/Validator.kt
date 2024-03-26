package com.arkul.mychat.utilities.validator

import com.arkul.mychat.utilities.validator.models.ValidationResult

interface Validator {
     fun validate(): ValidationResult
}