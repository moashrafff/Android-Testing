package com.moashraf.testingcourse.flows

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.Test

class FlowsPlaygroundTest {

    // 1- testing the flow itself
    // 2- testing the flow consumer

    @Test
    fun `Test flow itself`() = runTest {
        val flow = flowOf(1, 2, 3, 4)
        val res = flow.toList()
        assertEquals(res, listOf(1, 2, 3, 4))
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
        val res = mutableListOf<Int>()

        runCatching {
            val flow = flow {
                emit(1)
                throw IllegalStateException("Something went wrong")
            }

            flow.collect {
                res.add(it)
            }
        }.onFailure {
            res.add(-1)
        }

        assertEquals(res, listOf(1, -1))
    }


}