package com.moashraf.testingcourse.testdoubles

import io.mockk.mockk
import junit.framework.TestCase
import org.junit.Test

class UserManagerTest {

    // test dummy logger
    @Test
    fun `Given user when add user then return one item list`() {
        val logger : Logger = mockk()
        val userManager = UserManager(logger = logger)
        val user = User(userName = "Mohamed")
        userManager.addUser(user)
        TestCase.assertEquals(1, userManager.users.size)
    }
}