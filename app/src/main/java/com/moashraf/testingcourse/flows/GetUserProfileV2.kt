package com.moashraf.testingcourse.flows

import com.moashraf.testingcourse.coroutines.Profile
import com.moashraf.testingcourse.coroutines.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retry

class GetUserProfileV2(private val userRepository: UserRepository, private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) {

    suspend fun getProfileDataAsync() = flow {
        val name = userRepository.getName()
        val friends = userRepository.getFriends()
        val rate = userRepository.getRate()
        val profile = Profile(
            name = name,
            friends = friends,
            rate = rate
        )
        emit(Result.success(profile))
    }.retry(2) {
        (it is Exception).also { delay(1000) }
    }.catch {
        emit(Result.failure(it))
    }.flowOn(ioDispatcher)

}
