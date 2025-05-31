package com.moashraf.testingcourse.testdoubles

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Test

class CalculatorTest {

    // Test Strict vs Relaxed Testing
    @Test
    fun `Given two numbers when add then return sum`() {
        val dependency1: Dependency1 = mockk(relaxed = true)
        val dependency2: Dependency2 = mockk()
        every { dependency2.value } returns 3
        val calculator = Calculator(dependency1 = dependency1, dependency2 = dependency2)
        val result = calculator.calculate()
        assertEquals(3, result)
    }

    // Test Spy can stub too!
    @Test
    fun `Given two numbers Spy when add then return sum`() {
        val dependency1: Dependency1 = mockk()
        val dependency2 = Dependency2(4)
        val dependency2Spy = spyk(dependency2)
        every { dependency1.value } returns 3
        every { dependency2Spy.value } returns 7
        val calculator = Calculator(dependency1 = dependency1, dependency2 = dependency2Spy)
        val result = calculator.calculate()
        assertEquals(10, result)
    }

    // Test Argument Capturing and Matching using slot
    @Test
    fun `Given two numbers args catching when add then return sum`() {
        val mathService: MathService = mockk()
        val calculator = CalculatorV2(mathService)
        val slot = slot<Int>()
        every { mathService.calculate(capture(slot), any()) } answers { arg<Int>(0) + arg<Int>(1) }
        val result = calculator.add(2)

        assertEquals(12, result)

        assertEquals(2, slot.captured)
    }

    // Test Argument Capturing and Matching using with args
    @Test
    fun `Given two numbers args catching with args when add then return sum`() {
        val mathService: MathService = mockk()
        val calculator = CalculatorV2(mathService)
        every { mathService.calculate(any(), any()) } answers { arg<Int>(0) + arg<Int>(1) }
        val result = calculator.add(2)

        assertEquals(12, result)

        verify {
            mathService.calculate(withArg {
                assertEquals(2, it)
            }, any())
        }
    }

}