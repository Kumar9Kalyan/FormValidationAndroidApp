package com.example.cleanarchformvalidation.domain.use_case

import android.util.Patterns

class ValidationPassword {

    fun execute(password: String): ValidationResult{
        if(password.length < 9){
            return ValidationResult(
                false,
                "Password should have at least 9 characters"
            )
        }
        val containsDigitsAndLetters = password.any { it.isDigit() } && password.any { it.isLetter() }
        if(!containsDigitsAndLetters){
            return ValidationResult(false,"Password should contain at least one digit and letter")
        }
        return ValidationResult(true)
    }
}