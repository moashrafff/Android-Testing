package com.moashraf.testingcourse.flows

import app.cash.turbine.test
import com.moashraf.testingcourse.coroutines.Friend
import com.moashraf.testingcourse.coroutines.GetUserProfile
import com.moashraf.testingcourse.coroutines.GetUserProfileViewModel
import com.moashraf.testingcourse.coroutines.Profile
import com.moashraf.testingcourse.coroutines.ProfileUiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class GetUserProfileViewModelV2Test {
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Given success scenario when get user profile then return profile data flow`() = runTest {
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

        val expectedProfile = Profile(
            name = "Nadine",
            friends = listOf(Friend(id = "1", name = "Sara"), Friend(id = "2", name = "Menna")),
            rate = 5f
        )

        viewModel.profileState.test {
            awaitItem() shouldBeEqualTo ProfileUiState.Idle
            awaitItem() shouldBeEqualTo ProfileUiState.Loading
            (awaitItem() as ProfileUiState.Success).profile shouldBeEqualTo expectedProfile
        }

    }

    //add advanceUntilIdle if you want to observe the last state
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Given error scenario when get user profile then return exception`() = runTest {
        val useCase: GetUserProfile = mockk()
        coEvery { useCase.getProfileDataAsync() } throws IllegalStateException("Something went wrong")

        val viewModel = GetUserProfileViewModel(useCase, testDispatcher)
        viewModel.profileState.value

//        advanceUntilIdle()
        viewModel.profileState.test {
            awaitItem() shouldBeEqualTo ProfileUiState.Idle
            awaitItem() shouldBeEqualTo ProfileUiState.Loading
            (awaitItem() as ProfileUiState.Error).message shouldBeEqualTo "Something went wrong"
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun close() {
        Dispatchers.resetMain()
    }
}