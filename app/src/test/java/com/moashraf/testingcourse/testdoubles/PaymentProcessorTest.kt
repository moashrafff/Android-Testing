package com.moashraf.testingcourse.testdoubles

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Test

class PaymentProcessorTest {

    // Test stubs
    @Test
    fun `Given payment processor when pay then return true`() {
        val paymentService : PaymentService = mockk()
        val paymentProcessor = PaymentProcessor(paymentService = paymentService)
        every { paymentService.processPayment(any()) } returns true
        val result = paymentProcessor.pay(100.0)
        assertTrue(result)
    }

}