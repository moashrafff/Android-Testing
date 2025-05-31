package com.moashraf.testingcourse.testdoubles

import io.mockk.every
import io.mockk.mockk
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

}