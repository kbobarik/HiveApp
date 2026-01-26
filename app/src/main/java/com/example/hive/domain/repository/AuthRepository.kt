package com.example.hive.domain.repository

import com.example.hive.data.local.SharedPreferenceHelper
import com.example.hive.domain.ResultState
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import jakarta.inject.Inject

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<Unit>
    suspend fun signUp(email: String, password: String): Result<Unit>
}