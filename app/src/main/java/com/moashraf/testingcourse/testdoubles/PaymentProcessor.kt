package com.moashraf.testingcourse.testdoubles

interface PaymentService{
    fun processPayment(amount: Double) : Boolean
}

class PaymentProcessor (val paymentService: PaymentService){
    fun pay(amount: Double) : Boolean{
        val result = paymentService.processPayment(amount)
        return result
    }
}