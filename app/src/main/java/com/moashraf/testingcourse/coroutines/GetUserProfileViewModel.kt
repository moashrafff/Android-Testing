package com.moashraf.testingcourse.coroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GetUserProfileViewModel(private val profileUserCase: GetUserProfile, private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) : ViewModel() {

    private var _profileState = MutableStateFlow<ProfileUiState>(ProfileUiState.Idle)
    val profileState = _profileState

    init {
        getUserProfile()
    }

    private fun getUserProfile() {
        viewModelScope.launch(ioDispatcher) {
            _profileState.value = ProfileUiState.Loading
            runCatching {
                profileUserCase.getProfileDataAsync()
            }
                .onSuccess {
                    _profileState.value = ProfileUiState.Success(it)
                }.onFailure {
                    _profileState.value = ProfileUiState.Error(it.message ?: "Something went wrong")
                }
        }

    }
}