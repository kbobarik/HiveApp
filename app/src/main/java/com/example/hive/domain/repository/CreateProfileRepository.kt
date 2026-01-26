package com.example.hive.domain.repository

import android.util.Log
import com.example.hive.domain.Users
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import jakarta.inject.Inject


interface CreateProfileRepository {
    suspend fun addAvatar(byteArray: ByteArray, nickname: String): Result<Unit>
    suspend fun addUser(nickname: String): Result<Unit>
}