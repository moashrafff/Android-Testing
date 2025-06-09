package com.moashraf.testingcourse.flows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moashraf.testingcourse.coroutines.ProfileUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GetUserProfileViewModelV2(private val profileUserCase: GetUserProfileV2, private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) : ViewModel() {

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
//                    _profileState.value = ProfileUiState.Success(it)
                }.onFailure {
                    _profileState.value = ProfileUiState.Error(it.message ?: "Something went wrong")
                }
        }

    }
}