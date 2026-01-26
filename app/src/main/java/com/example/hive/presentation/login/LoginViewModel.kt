package com.example.hive.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hive.domain.repository.AuthRepository
import com.example.hive.domain.ResultState
import com.example.hive.domain.useCase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor( private val signInUseCase: SignInUseCase) : ViewModel() {
    private val _stateSignIn = MutableStateFlow<ResultState>(ResultState.Idle)
    val stateSignIn = _stateSignIn.asStateFlow()
    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _stateSignIn.value = ResultState.Loading

            val result = signInUseCase(email, password)

            _stateSignIn.value = result.fold(
                onSuccess = { ResultState.Success("Success sign in") },
                onFailure = { ResultState.Error(it.message ?: "Ошибка") }
            )
        }
    }
}