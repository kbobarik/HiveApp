package com.example.hive.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hive.data.AuthRepository
import com.example.hive.data.SharedPreferenceHelper
import com.example.hive.domain.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {
    private val _stateSignUp = MutableStateFlow<ResultState>(ResultState.Idle)
    val stateSignUp = _stateSignUp.asStateFlow()
    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _stateSignUp.value = ResultState.Loading
            try {
                repository.signUp(email, password)
                _stateSignUp.value = ResultState.Success("Success sign up")
            } catch (e: Exception) {
                _stateSignUp.value = ResultState.Error(e.message ?: "Unknown error")
                Log.e("sing up", e.message ?: "Unknown error")
            }
        }
    }
}