package com.example.hive.domain.useCase

import com.example.hive.domain.repository.AuthRepository
import com.example.hive.domain.repository.CreateProfileRepository
import jakarta.inject.Inject

class CreateProfileUseCase @Inject constructor(private val createProfileRepository: CreateProfileRepository) {
    suspend fun addAvtar(byteArray: ByteArray, nickname: String): Result<Unit> {
        if (byteArray.isEmpty() || nickname.isBlank()) {
            return Result.failure(Exception("Пустые поля"))
        }

        return createProfileRepository.addAvatar(byteArray, nickname)
    }

    suspend fun addUser(nickname: String): Result<Unit> {
        if (nickname.isBlank()) {
            return Result.failure(Exception("Пустые поля"))
        }

        return createProfileRepository.addUser(nickname)
    }
}