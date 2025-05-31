package com.moashraf.testingcourse.testdoubles

interface UserService {
    fun getUsersCount(): Int
}

class PremiumUserManager(val userService: UserService) {
    fun getUsersCount() = userService.getUsersCount()
}