package com.moashraf.testingcourse.flows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moashraf.testingcourse.coroutines.ProfileUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class GetUserProfileViewModelV2(
    private val profileUserCase: GetUserProfileV2,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private var _profileState = MutableStateFlow<ProfileUiState>(ProfileUiState.Idle)
    val profileState = _profileState

    private suspend fun getUserProfile() {
        profileUserCase.getProfileDataAsync()
            .onStart {
                _profileState.update { ProfileUiState.Loading }
            }.onEach {
                val profile = it.getOrNull() ?: return@onEach
                when {
                    it.isSuccess -> _profileState.update { ProfileUiState.Success(profile) }
                    it.isFailure -> _profileState.update { ProfileUiState.Error(it.toString()) }
                }
            }.catch {
                _profileState.update { ProfileUiState.Error(it.toString()) }
            }.launchIn(viewModelScope)


    }
}