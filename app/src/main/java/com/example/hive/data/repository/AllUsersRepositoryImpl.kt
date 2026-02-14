package com.example.hive.data.repository

import com.example.hive.domain.ChatPreviewDto
import com.example.hive.domain.Users
import com.example.hive.domain.repository.AllUsersRepository
import com.example.hive.domain.repository.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import javax.inject.Inject

class AllUsersRepositoryImpl @Inject constructor(
    private val supabase: SupabaseClient
) : AllUsersRepository {
    override suspend fun getAllUsers(): Result<List<Users>> {
        return try {
            val chats = supabase
                .from("users")
                .select(){
                    filter {
                        Users::id neq supabase.auth.currentUserOrNull()!!.id
                    }
                }
                .decodeList<Users>()
                .sortedByDescending { it.nickname }

            Result.success(chats)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}