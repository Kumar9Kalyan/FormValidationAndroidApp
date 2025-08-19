package com.example.cleanarchformvalidation.presentation

import app.cash.turbine.test
import com.example.cleanarchformvalidation.TestDispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
@OptIn(ExperimentalCoroutinesApi::class)
class FlowViewModelTest {

    private lateinit var viewModel: FlowViewModel
    private lateinit var testDispatchers: TestDispatchers


    @Before
    fun setUp() {
        testDispatchers = TestDispatchers()
        Dispatchers.setMain(testDispatchers.testDispatcher)
        viewModel = FlowViewModel(testDispatchers)
    }

    @Test
    fun `countDownFlow, properly counts down from 5 to 0`() = runBlocking{

        viewModel.countDownFlow.test {
            for(i in 5 downTo 0){
                testDispatchers.testDispatcher.scheduler.apply { advanceTimeBy(1000L); runCurrent() }
                val emission = awaitItem()
                assert(emission == i)
        }
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `squareNumber, number properly squared`() = runTest{

        val job = launch {
            viewModel.sharedFlow.test {
                val emission = awaitItem()
                assert(emission == 16)
                cancelAndConsumeRemainingEvents()
            }
        }
        viewModel.squareNumber(4)
        job.join()
        job.cancel()

    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

}