package com.moashraf.testingcourse.flows

import app.cash.turbine.test
import com.moashraf.testingcourse.coroutines.Profile
import com.moashraf.testingcourse.coroutines.ProfileUiState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class FlowsPlaygroundTurbineTest {

    // trying turbine with kluent
    @Test
    fun `Test flow itself`() = runTest {
        val flow = flowOf(1, 2, 3, 4)
        flow.test {
            1 shouldBeEqualTo awaitItem()
            2 shouldBeEqualTo awaitItem()
            3 shouldBeEqualTo awaitItem()
            4 shouldBeEqualTo awaitItem()
            awaitComplete()
        }
    }

    @Test
    fun `Test flow consumer`() = runTest {
        val flow = flowOf(1, 2, 3, 4)
        val res = mutableListOf<Int>()

        flow.collect {
            res.add(it)
        }

        assertEquals(res, listOf(1, 2, 3, 4))
    }

    @Test
    fun `Test flow consumer continuously`() = runTest {
        val flow = flow {
            for (i in 1..4) {
                emit(i)
            }
        }
        val res = mutableListOf<Int>()

        flow.collect {
            res.add(it)
        }

        assertEquals(res, listOf(1, 2, 3, 4))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test flow consumer continuously with delay`() = runTest {
        val flow = flow {
            for (i in 1..4) {
                emit(i)
                delay(100)
            }
        }
        val res = mutableListOf<Int>()

        flow.onEach {
            res.add(it)
        }.launchIn(this)

        advanceUntilIdle()

        assertEquals(res, listOf(1, 2, 3, 4))
    }

    @Test
    fun `Test flow consumer with exception`() = runTest {

        val flow = flow {
            emit(1)
            throw IllegalStateException("Something went wrong")
        }

        flow.test {
            assertEquals(1, awaitItem())
            assertEquals("Something went wrong", awaitError().message)
        }

    }

    @Test
    fun `Test hot flow itself`() = runTest {
        val flow = flowOf(1, 2, 3, 4).stateIn(this)
        flow.test {
            4 shouldBeEqualTo awaitItem()
        }
    }

    @Test
    fun `Test mutable state flow itself`() = runTest {
        val flow = MutableStateFlow<ProfileUiState>(ProfileUiState.Idle)
        flow.tryEmit(ProfileUiState.Success(Profile("Mohamed", listOf(), 2.3f)))
        flow.test {
            ProfileUiState.Success(Profile("Mohamed", listOf(), 2.3f)) shouldBeEqualTo awaitItem()
        }
    }

    @Test
    fun `Test Shared flow itself with replay`() = runTest {
        val flow = MutableSharedFlow<Int>(replay = 1)
        flow.tryEmit(1)
        flow.test {
            1 shouldBeEqualTo awaitItem()
        }
    }

    @Test
    fun `Test Shared flow itself`() = runTest {
        val flow = MutableSharedFlow<Int>()
        val job = launch(start = CoroutineStart.LAZY) {
            flow.emit(1)
        }
        flow.test {
            job.start()
            1 shouldBeEqualTo awaitItem()
        }
    }

    @Test
    fun `Test Shared flow itself whileSubscribed()`() = runTest {
        val flow = flowOf("Event 1", "Event 2", "Event 3")
        val sharedFlow = flow.onCompletion { println("Shared Flow Completed") }.shareIn(scope = this, replay = 1, started = SharingStarted.WhileSubscribed())
        sharedFlow.test {
            "Event 1" shouldBeEqualTo awaitItem()
            "Event 2" shouldBeEqualTo awaitItem()
            "Event 3" shouldBeEqualTo awaitItem()
        }
        coroutineContext.cancelChildren()
    }

    @Test
    fun `Test Shared flow itself Lazily`() = runTest {
        val flow = flowOf("Event 1", "Event 2", "Event 3")
        val sharedFlow = flow.onCompletion { println("Shared Flow Completed") }.shareIn(scope = this, replay = 1, started = SharingStarted.Lazily)
        sharedFlow.test {
            "Event 1" shouldBeEqualTo awaitItem()
            "Event 2" shouldBeEqualTo awaitItem()
            "Event 3" shouldBeEqualTo awaitItem()
        }
        coroutineContext.cancelChildren()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test Shared flow itself eagerly`() = runTest(UnconfinedTestDispatcher()) {
        val flow = flowOf("Event 1", "Event 2", "Event 3")
        val sharedFlow = flow.onCompletion { println("Shared Flow Completed") }.shareIn(scope = this, replay = 1, started = SharingStarted.Eagerly)
        sharedFlow.test {
            "Event 3" shouldBeEqualTo awaitItem()
        }
        coroutineContext.cancelChildren()
    }

}