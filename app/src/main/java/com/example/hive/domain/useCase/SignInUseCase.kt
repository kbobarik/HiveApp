package com.example.hive.domain.useCase

import com.example.hive.domain.repository.AuthRepository
import jakarta.inject.Inject

class SignInUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(Exception("Пустые поля"))
        }

        return authRepository.signIn(email, password)
    }
}