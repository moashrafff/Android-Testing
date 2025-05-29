package com.moashraf.testingcourse.testdoubles

class UserManager(val logger: Logger) {

    val users = mutableListOf<User>()

    fun addUser(user: User){
        users.add(user)
    }

}

data class User(val userName: String)