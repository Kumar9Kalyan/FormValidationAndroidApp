package com.example.cleanarchformvalidation.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cleanarchformvalidation.DispatcherProvider

class FlowViewModelFactory(
    private val dispatchers: DispatcherProvider
) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(FlowViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return FlowViewModel(dispatchers) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}