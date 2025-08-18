package com.example.cleanarchformvalidation.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cleanarchformvalidation.ui.theme.CleanArchFormValidationTheme

class FlowActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CleanArchFormValidationTheme{
                val viewModel = viewModel<FlowViewModel> ()
                val time = viewModel.countDownFlow.collectAsState(10)

                Box(modifier = Modifier.fillMaxSize()) {
                    Text(text = time.value.toString(),
                        fontSize = 30.sp,
                        modifier = Modifier.align(Alignment.Center)
                        )
                }
            }
        }
    }

}
