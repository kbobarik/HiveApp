package com.example.hive.domain.useCase

import com.example.hive.domain.ChatPreviewDto
import com.example.hive.domain.Users
import com.example.hive.domain.repository.AllUsersRepository
import com.example.hive.domain.repository.CreateProfileRepository
import jakarta.inject.Inject

class AllUsersUseCase@Inject constructor(private val allUsersRepository: AllUsersRepository)  {
    suspend operator fun invoke(): Result<List<Users>> {
        return allUsersRepository.getAllUsers()
    }
}