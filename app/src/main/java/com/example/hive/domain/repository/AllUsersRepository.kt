package com.example.hive.domain.repository

import com.example.hive.domain.Users

interface AllUsersRepository {
    suspend fun getAllUsers(): Result<List<Users>>
}