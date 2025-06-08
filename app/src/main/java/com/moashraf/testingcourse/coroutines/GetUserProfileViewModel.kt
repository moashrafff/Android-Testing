package com.moashraf.testingcourse.coroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GetUserProfileViewModel(private val profileUserCase: GetUserProfile) : ViewModel() {

    private var _profileState = MutableStateFlow<ProfileUiState>(ProfileUiState.Idle)
    val profileState = _profileState

    init {
        getUserProfile()
    }

    private fun getUserProfile() {
        viewModelScope.launch {
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