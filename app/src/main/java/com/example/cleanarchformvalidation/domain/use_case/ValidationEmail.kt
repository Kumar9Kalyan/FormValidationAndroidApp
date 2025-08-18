package com.example.cleanarchformvalidation.domain.use_case

import android.util.Patterns

class ValidationEmail {

    fun execute(email: String): ValidationResult{
        if(email.isBlank()){
            return ValidationResult(
                false,
                "Email can't be blank"
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return ValidationResult(false,"Invalid email")
        }
        return ValidationResult(true)
    }
}