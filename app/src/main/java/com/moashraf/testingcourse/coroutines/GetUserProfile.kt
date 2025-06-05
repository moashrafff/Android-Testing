package com.moashraf.testingcourse.coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetUserProfile(private val userRepository: UserRepository) {

    suspend fun getProfileDataAsync() = coroutineScope {
        val name = async { userRepository.getName() }
        val friends = async { userRepository.getFriends() }
        val rate = async { userRepository.getRate() }
        Profile(
            name = name.await(),
            friends = friends.await(),
            rate = rate.await()
        )
    }

    suspend fun getProfileDataSync() = Profile(
        name = "Mohamed",
        friends = listOf(Friend(id = "1", name = "Mostafa"), Friend(id = "2", name = "Ahmed")),
        rate = 2.3f
    )

}

interface UserRepository {
    suspend fun getName(): String
    suspend fun getFriends(): List<Friend>
    suspend fun getRate(): Float
}

class FakeUserRepositoryImpl : UserRepository{
    override suspend fun getName(): String = "Nadine"

    override suspend fun getFriends(): List<Friend> = listOf(Friend(id = "1", name = "Sara"), Friend(id = "2", name = "Menna"))

    override suspend fun getRate(): Float = 5f
}


data class Friend(
    val id : String,
    val name : String
)

data class Profile(
    val name : String,
    val friends : List<Friend>,
    val rate : Float
)
