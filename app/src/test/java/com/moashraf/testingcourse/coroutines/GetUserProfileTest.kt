package com.moashraf.testingcourse.coroutines

import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class GetUserProfileTest {
    @Test
    fun `Given happy scenario when get profile data then return profile data`() = runTest {
        val useCase = GetUserProfile(FakeUserRepositoryImpl())
        val result = useCase.getProfileDataAsync()
        val expectedProfile = Profile(
            name = "Nadine",
            friends = listOf(Friend(id = "1", name = "Sara"), Friend(id = "2", name = "Menna")),
            rate = 5f
        )
        assertEquals(expectedProfile, result)
    }
}