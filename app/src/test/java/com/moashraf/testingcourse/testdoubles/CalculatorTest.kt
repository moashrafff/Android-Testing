package com.moashraf.testingcourse.testdoubles

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Assert.*
import org.junit.Test

class CalculatorTest {

    // Test Strict vs Relaxed Testing
    @Test
    fun `Given two numbers when add then return sum`() {
        val dependency1 : Dependency1= mockk(relaxed = true)
        val dependency2 : Dependency2= mockk()
        every { dependency2.value } returns 3
        val calculator = Calculator(dependency1 = dependency1 , dependency2 = dependency2)
        val result = calculator.calculate()
        assertEquals(3, result)
    }

    // Test Spy can stub too!
    @Test
    fun `Given two numbers Spy when add then return sum`() {
        val dependency1 : Dependency1= mockk()
        val dependency2 = Dependency2(4)
        val dependency2Spy = spyk(dependency2)
        every { dependency1.value } returns 3
        every { dependency2Spy.value } returns 7
        val calculator = Calculator(dependency1 = dependency1 , dependency2 = dependency2Spy)
        val result = calculator.calculate()
        assertEquals(10, result)
    }

}