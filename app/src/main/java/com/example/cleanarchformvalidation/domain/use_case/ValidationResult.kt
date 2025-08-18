package com.example.cleanarchformvalidation.domain.use_case

data class ValidationResult (
    val success: Boolean,
    val errorMessage: String? = null
)