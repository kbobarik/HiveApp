package com.example.hive.presentation.allUsers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hive.domain.ChatPreviewDto
import com.example.hive.domain.ResultState
import com.example.hive.domain.Users
import com.example.hive.domain.useCase.AllUsersUseCase
import com.example.hive.domain.useCase.MessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllUsersViewModel @Inject constructor(private val allUsersUseCase: AllUsersUseCase) :
    ViewModel() {
    private val _stateGetUsers = MutableStateFlow<ResultState>(ResultState.Idle)
    val stateGetUsers = _stateGetUsers.asStateFlow()

    private val _users = MutableStateFlow<List<Users>>(emptyList())
    val users = _users.asStateFlow()

    fun getAllUsers() {
        viewModelScope.launch {
            _stateGetUsers.value = ResultState.Loading

            val result = allUsersUseCase()

            result
                .onSuccess { chatList ->
                    _users.value = chatList
                    _stateGetUsers.value = ResultState.Success("Users loaded")
                }
                .onFailure {
                    _stateGetUsers.value =
                        ResultState.Error(it.message ?: "Ошибка")
                }
        }
    }
}