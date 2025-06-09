package com.moashraf.testingcourse.flows

import com.moashraf.testingcourse.coroutines.Friend
import com.moashraf.testingcourse.coroutines.Profile
import com.moashraf.testingcourse.coroutines.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldBeTrue
import org.junit.Test
import kotlin.coroutines.ContinuationInterceptor

class GetUserProfileV2Test {

    @Test
    fun `Given get profile useCase when calling getProfileDataAsync then return success`() =
        runTest {
            val mockRepo: UserRepository = mockk()
            val useCase = GetUserProfileV2(
                mockRepo, this.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher
            )
            val expectedProfile = Profile(
                name = "Nadine",
                friends = listOf(Friend(id = "1", name = "Sara"), Friend(id = "2", name = "Menna")),
                rate = 5f
            )

            coEvery { mockRepo.getName() } coAnswers { delay(1000000); "Nadine" }
            coEvery { mockRepo.getFriends() } coAnswers {
                listOf(
                    Friend(id = "1", name = "Sara"), Friend(id = "2", name = "Menna")
                )
            }
            coEvery { mockRepo.getRate() } coAnswers { 5f }

            val flow = useCase.getProfileDataAsync()

            flow.collect {
                it.isSuccess.shouldBeTrue()
                it.onSuccess {
                    it shouldBeEqualTo expectedProfile
                }
            }
        }

    @Test
    fun `Given get profile useCase when calling getProfileDataAsync then return Error`() =
        runTest {
            val mockRepo: UserRepository = mockk()
            val useCase = GetUserProfileV2(
                mockRepo, this.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher
            )

            coEvery { mockRepo.getName() } coAnswers { delay(1000); throw IllegalStateException() }

            val flow = useCase.getProfileDataAsync()

            flow.collect {
                it.isSuccess.shouldBeFalse()
                it.onFailure {
                    it.shouldBeInstanceOf<IllegalStateException>()
                }
            }
        }

}