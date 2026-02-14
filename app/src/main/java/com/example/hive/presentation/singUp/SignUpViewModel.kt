package com.example.hive.presentation.singUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hive.domain.ResultState
import com.example.hive.domain.useCase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpUseCase: SignUpUseCase) : ViewModel() {
    private val _stateSignUp = MutableStateFlow<ResultState>(ResultState.Idle)
    val stateSignUp = _stateSignUp.asStateFlow()
    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _stateSignUp.value = ResultState.Loading
            val result = signUpUseCase(email,password)
            _stateSignUp.value = result.fold(
                onSuccess = { ResultState.Success("Success sign up") },
                onFailure = { ResultState.Error(it.message ?: "Ошибка") }
            )
        }
    }
}