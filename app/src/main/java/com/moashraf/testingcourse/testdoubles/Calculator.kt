package com.moashraf.testingcourse.testdoubles

data class Dependency1(val value: Int)
data class Dependency2(val value: Int)

class Calculator(val dependency1: Dependency1, val dependency2: Dependency2) {
    fun calculate() = dependency1.value + dependency2.value
}