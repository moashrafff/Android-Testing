package com.moashraf.testingcourse.coroutines

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetUserProfileViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Given success scenario when get user profile then return profile data`() = runTest {
        val useCase: GetUserProfile = mockk()
        coEvery { useCase.getProfileDataAsync() } coAnswers {
            Profile(
                name = "Nadine",
                friends = listOf(Friend(id = "1", name = "Sara"), Friend(id = "2", name = "Menna")),
                rate = 5f
            )
        }

        val viewModel = GetUserProfileViewModel(useCase, testDispatcher)
        viewModel.profileState.value

        advanceUntilIdle()

        assertEquals(
            ProfileUiState.Success(
                Profile(
                    name = "Nadine", friends = listOf(
                        Friend(id = "1", name = "Sara"), Friend(id = "2", name = "Menna")
                    ), rate = 5f
                )
            ), viewModel.profileState.value
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Given error scenario when get user profile then return exception`() = runTest {
        val useCase: GetUserProfile = mockk()
        coEvery { useCase.getProfileDataAsync() } throws IllegalStateException("Something went wrong")

        val viewModel = GetUserProfileViewModel(useCase, testDispatcher)
        viewModel.profileState.value

        advanceUntilIdle()

        assertEquals(
            ProfileUiState.Error("Something went wrong"), viewModel.profileState.value
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun close() {
        Dispatchers.resetMain()
    }

}