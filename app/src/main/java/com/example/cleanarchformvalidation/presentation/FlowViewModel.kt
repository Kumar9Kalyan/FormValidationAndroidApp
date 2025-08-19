package com.example.cleanarchformvalidation.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchformvalidation.DispatcherProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class FlowViewModel(
    private val dispatchers: DispatcherProvider
): ViewModel() {

    //This is a coldFlow, i.e., it emits value if there is a collector
    val countDownFlow = flow < Int>{
        val startingValue = 5
        var currentValue = startingValue
        emit(currentValue)
        while (currentValue>0){
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }.flowOn(dispatchers.main)

    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<Int>()
    val sharedFlow = _sharedFlow.asSharedFlow()


    init {
        collectFlow()
        squareNumber(3)
        viewModelScope.launch(dispatchers.main) {
            sharedFlow.collect {
                delay(1000L)
                println("FLOW1: The received number is: $it")
            }

        }
        viewModelScope.launch(dispatchers.main) {
            sharedFlow.collect {
                delay(1000L)
                println("FLOW2: The received number is: $it")
            }
        }
    }

    fun squareNumber(number: Int){
        viewModelScope.launch(dispatchers.main) {

            _sharedFlow.emit(number * number)
        }
    }
    fun incrementCounter(){
        _stateFlow.value += 100
    }
    private fun collectFlow() {

//        countDownFlow.onEach {
//            println(it)
//        }.launchIn(viewModelScope)

        viewModelScope.launch {
//            val reducedValue = countDownFlow.fold(100) { accumulator, value ->
//                accumulator + value
//            }
//            println("Count is $reducedValue")
            val flow1 = (1..5).asFlow()
            flow1.flatMapConcat { val1->
                flow {
                    emit(val1+1)
                    delay(1000L)
                    emit(val1+2)
                }
            }.collect { valX->
            println(valX)
            }
        }
    }
}