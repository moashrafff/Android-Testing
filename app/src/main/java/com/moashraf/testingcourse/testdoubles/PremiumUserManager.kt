package com.moashraf.testingcourse.testdoubles

interface UserService {
    fun getUsersCount(): Int
}

class UserServiceImpl : UserService {
    override fun getUsersCount(): Int = 20
}

class PremiumUserManager(val userService: UserService) {
    fun getUsersCount() = userService.getUsersCount()
}