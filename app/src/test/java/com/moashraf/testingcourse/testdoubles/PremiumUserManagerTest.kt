package com.moashraf.testingcourse.testdoubles

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.junit.Test


class PremiumUserManagerTest {

    // Test Mock
    @Test
    fun `Given premium user manager when get users count then return 100`() {
        val userService : UserService = mockk()
        every { userService.getUsersCount() } returns 100
        val premiumUserManager = PremiumUserManager(userService = userService)
        val result = premiumUserManager.getUsersCount()
        assertEquals(100,result)
        verify(atLeast = 1) { userService.getUsersCount() }
    }

    // Test Spy
    @Test
    fun `Given premium user manager spy when get users count then return 20`() {
        val userService : UserService = UserServiceImpl()
        val userServiceSpy = spyk(userService)
        val premiumUserManager = PremiumUserManager(userService = userServiceSpy)
        val result = premiumUserManager.getUsersCount()
        assertEquals(20,result)
        verify(atLeast = 1) { userServiceSpy.getUsersCount() }
    }

}