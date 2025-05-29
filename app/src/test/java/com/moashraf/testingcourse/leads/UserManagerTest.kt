package com.moashraf.testingcourse.leads

import com.moashraf.testingcourse.testdoubles.Logger
import io.mockk.mockk
import com.moashraf.testingcourse.testdoubles.User
import com.moashraf.testingcourse.testdoubles.UserManager
import junit.framework.TestCase.assertEquals
import org.junit.Test

class UserManagerTest {

    // test dummy logger
    @Test
    fun `Given user when add user then return one item list`() {
        val logger : Logger = mockk()
        val userManager = UserManager(logger = logger)
        val user = User(userName = "Mohamed")
        userManager.addUser(user)
        assertEquals(1, userManager.users.size)
    }

}