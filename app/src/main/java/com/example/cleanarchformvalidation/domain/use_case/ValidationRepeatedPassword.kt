package com.example.cleanarchformvalidation.domain.use_case

class ValidationRepeatedPassword {

    fun execute(password: String ,repeatedPassword: String): ValidationResult{
        if(password != repeatedPassword){
            return ValidationResult(
                false,
                "Passwords mismatch"
            )
        }

        return ValidationResult(true)
    }
}