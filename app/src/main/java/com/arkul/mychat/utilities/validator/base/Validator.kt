package com.arkul.mychat.utilities.validator.base

import com.arkul.mychat.utilities.validator.models.ValidationResult

interface Validator {
     fun validate(): ValidationResult
}