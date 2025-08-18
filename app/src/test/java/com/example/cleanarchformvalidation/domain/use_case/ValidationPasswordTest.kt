package com.example.cleanarchformvalidation.domain.use_case

import org.junit.Before
import org.junit.Test

class ValidationPasswordTest {

    private lateinit var validatePassword: ValidationPassword

    @Before
    fun setUp() {

        validatePassword = ValidationPassword()

    }

    @Test
    fun `Password is letter-only, returns error`(){

        val result = validatePassword.execute("abcdef")

        assert(!result.success)
    }

}