package com.example.hive.presentation.createProfile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hive.domain.ResultState
import com.example.hive.domain.repository.CreateProfileRepository
import com.example.hive.domain.useCase.CreateProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CreateProfileViewModel @Inject constructor(private val createProfileUseCase: CreateProfileUseCase) :
    ViewModel() {
    private val _stateAddAvatar = MutableStateFlow<ResultState>(ResultState.Idle)
    val stateAddAvatar = _stateAddAvatar.asStateFlow()
    private val _stateAddUser = MutableStateFlow<ResultState>(ResultState.Idle)
    val stateAddUser = _stateAddUser.asStateFlow()


    fun addAvatar(byteArray: ByteArray, nickname: String) {
        viewModelScope.launch {
            _stateAddAvatar.value = ResultState.Loading

            val result = createProfileUseCase.addAvtar(byteArray, nickname)

            _stateAddAvatar.value = result.fold(
                onSuccess = { ResultState.Success("Success add avatar") },
                onFailure = { ResultState.Error(it.message ?: "Ошибка") }
            )
            if(_stateAddAvatar.value is ResultState.Success){
                Log.d("add avatar", "Success add avatar")
            }else{
                Log.e("add avatar", "Failed add avatar")
            }

        }
    }

    fun addUser(nickname: String) {
        viewModelScope.launch {
            _stateAddUser.value = ResultState.Loading

            val result = createProfileUseCase.addUser(nickname)

            _stateAddUser.value = result.fold(
                onSuccess = { ResultState.Success("Success add user") },
                onFailure = { ResultState.Error(it.message ?: "Ошибка") }
            )
            if(_stateAddUser.value is ResultState.Success){
                Log.d("add avatar", "Success add user")
            }else{
                Log.e("add avatar", "Failed add user")
            }
        }
    }
}