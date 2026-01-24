package com.example.hive.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hive.data.AuthRepository
import com.example.hive.domain.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {
    private val _stateSignIn = MutableStateFlow<ResultState>(ResultState.Idle)
    val stateSignIn = _stateSignIn.asStateFlow()
    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _stateSignIn.value = ResultState.Loading
            try {
                repository.signIn(email, password)
                _stateSignIn.value = ResultState.Success("Success sign in")
            } catch (e: Exception) {
                
                _stateSignIn.value = ResultState.Error(e.message ?: "Unknown error")
                Log.e("sing in", e.message ?: "Unknown error")
            }

        }
    }
}