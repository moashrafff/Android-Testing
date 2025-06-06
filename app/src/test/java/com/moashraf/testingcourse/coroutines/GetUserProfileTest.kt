package com.moashraf.testingcourse.coroutines

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.currentTime
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Given happy Delayed scenario when get profile data then return accurate time`() = runTest {
        val useCase = GetUserProfile(FakeUserRepositoryImplV2())
        useCase.getProfileDataAsync()
        assertEquals(1000, currentTime)
    }

    @Test
    fun `Given mock scenario when get profile data then return profile data`() = runTest {
        val mockRepo : UserRepository = mockk()
        coEvery { mockRepo.getName() } coAnswers { delay(1000000); "Nadine" }
        coEvery { mockRepo.getFriends() } coAnswers { listOf(Friend(id = "1", name = "Sara"), Friend(id = "2", name = "Menna")) }
        coEvery { mockRepo.getRate() } coAnswers { 5f }
        val result = GetUserProfile(mockRepo)

        val expectedProfile = Profile(
            name = "Nadine",
            friends = listOf(Friend(id = "1", name = "Sara"), Friend(id = "2", name = "Menna")),
            rate = 5f
        )
        assertEquals(expectedProfile, result.getProfileDataAsync())
    }
}