package com.example.cleanarchformvalidation.domain.use_case

import android.util.Patterns

class ValidationTerms {

    fun execute(acceptedTerms: Boolean): ValidationResult{
        if(!acceptedTerms){
            return ValidationResult(
                false,
                "Please accept the terms"
            )
        }

        return ValidationResult(true)
    }
}