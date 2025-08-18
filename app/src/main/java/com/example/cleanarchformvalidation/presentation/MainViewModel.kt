package com.example.cleanarchformvalidation.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchformvalidation.domain.use_case.ValidationEmail
import com.example.cleanarchformvalidation.domain.use_case.ValidationPassword
import com.example.cleanarchformvalidation.domain.use_case.ValidationRepeatedPassword
import com.example.cleanarchformvalidation.domain.use_case.ValidationTerms
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val validateEmail: ValidationEmail = ValidationEmail(),
    private val validatePassword: ValidationPassword = ValidationPassword(),
    private val validateRepeatedPassword: ValidationRepeatedPassword = ValidationRepeatedPassword(),
    private val validateTerms: ValidationTerms = ValidationTerms(),
): ViewModel() {

        var state by mutableStateOf(RegistrationFormState())

        private val validationEventChannel = Channel<ValidationEvent>()
        val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: RegistrationFormEvent){
        when(event){
            is RegistrationFormEvent.EmailChanged ->{
                state = state.copy(event.email)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegistrationFormEvent.RepeatedPasswordChanged->{
                state = state.copy(repeatedPassword =  event.repeatedPasswordChanged)
            }
            is RegistrationFormEvent.AcceptTerms-> {
                state = state.copy(acceptedTerms = event.isAccepted)
            }
            is RegistrationFormEvent.Submit->{
                    submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
        val repeatedPasswordChanged = validateRepeatedPassword.execute(state.password,state.repeatedPassword)
        val termsResult = validateTerms.execute(state.acceptedTerms)

        val hasError = listOf(
            emailResult,passwordResult,repeatedPasswordChanged,termsResult
        ).any { !it.success }

        if(hasError){
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatedPasswordError = repeatedPasswordChanged.errorMessage,
                termsError = termsResult.errorMessage
                )
            return
        }

        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }

    }

    sealed class ValidationEvent{
        object Success: ValidationEvent()
    }


}