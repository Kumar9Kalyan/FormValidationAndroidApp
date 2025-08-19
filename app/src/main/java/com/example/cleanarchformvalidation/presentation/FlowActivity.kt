package com.example.cleanarchformvalidation.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.cleanarchformvalidation.DefaultDispatchers
import com.example.cleanarchformvalidation.ui.theme.CleanArchFormValidationTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FlowActivity: ComponentActivity() {

    private val viewModel: FlowViewModel by viewModels(){
        FlowViewModelFactory(DefaultDispatchers())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        collectLatestLifecycleFlow(viewModel.stateFlow){number->
//        binding.tvCounter.text = number.toString()
        }




        setContent {
            CleanArchFormValidationTheme{
                val snackbarHostState = remember { SnackbarHostState() }
                val time = viewModel.stateFlow.collectAsState(0)
                LaunchedEffect(Unit) {
                    viewModel.stateFlow.collect { number->
                        snackbarHostState.showSnackbar("Value emitted is $number")
                    }

                }

                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) {paddingValues->


                    Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                        Text(
                            text = "Value is: ${time.value}",
                            fontSize = 30.sp,
                            modifier = Modifier.align(Alignment.Center)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.incrementCounter() },
                            modifier = Modifier.align(Alignment.BottomEnd)
                        ) {
                            Text(text = "Increment")
                        }
                    }
                }
            }
        }
    }

}
fun <T> ComponentActivity.collectLatestLifecycleFlow(flow: Flow<T>,collect:suspend (T) ->Unit){

    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED){
            flow.collectLatest(collect)
        }
    }

}

fun <T> ComponentActivity.collectLifecycleFlow(flow: Flow<T>,collect: suspend (T) -> Unit){

    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED){
            flow.collect(collect)
        }
    }
}
