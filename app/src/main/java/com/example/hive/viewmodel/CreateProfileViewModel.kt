package com.example.hive.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hive.data.AuthRepository
import com.example.hive.data.CreateProfileRepository
import com.example.hive.data.SupabaseModule
import com.example.hive.domain.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.annotation.meta.TypeQualifierNickname

@HiltViewModel
class CreateProfileViewModel @Inject constructor(private val repository: CreateProfileRepository) :
    ViewModel() {
    private val _stateAddAvatar = MutableStateFlow<ResultState>(ResultState.Idle)
    val stateAddAvatar = _stateAddAvatar.asStateFlow()
    private val _stateAddUser = MutableStateFlow<ResultState>(ResultState.Idle)
    val stateAddUser = _stateAddUser.asStateFlow()

    fun checkValidNickname(nickname: String): Boolean {
        var result = false
        viewModelScope.launch {
            result = repository.checkValidNickname(nickname)
        }
        return result
    }

    fun addAvatar(byteArray: ByteArray, nickname: String) {
        viewModelScope.launch {
            try {
                _stateAddAvatar.value = ResultState.Loading
                repository.addAvatar(byteArray, nickname)
                _stateAddAvatar.value = ResultState.Success("Success add avatar")

            } catch (e: Exception) {
                _stateAddAvatar.value = ResultState.Error(e.message ?: "Unknown error")
                Log.e("add avatar",e.message ?: "Unknown error" )
            }
        }
    }

    fun addUser(nickname: String){
        viewModelScope.launch {
            try {
                _stateAddUser.value = ResultState.Loading
                repository.addUser(nickname)
                _stateAddUser.value = ResultState.Success("Success add avatar")
            } catch (e: Exception) {
                _stateAddUser.value = ResultState.Error(e.message ?: "Unknown error")
                Log.e("add user",e.message ?: "Unknown error" )
            }
        }
    }
}