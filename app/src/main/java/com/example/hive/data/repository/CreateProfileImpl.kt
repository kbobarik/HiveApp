package com.example.hive.data.repository

import com.example.hive.data.extensions.mapError
import com.example.hive.domain.Users
import com.example.hive.domain.repository.CreateProfileRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import io.github.jan.supabase.storage.upload
import javax.inject.Inject

class CreateProfileImpl @Inject constructor(
    private val supabase: SupabaseClient
) : CreateProfileRepository {
    override suspend fun addAvatar(
        byteArray: ByteArray,
        nickname: String
    ): Result<Unit> = runCatching {
        val basket = supabase.storage["avatars"]
        basket.upload(
            "${nickname.replace(" ", "_")}.jpg",
            byteArray
        ) {
            upsert = true
        }
        val avatarUrl = supabase.storage
            .from("avatars")
            .publicUrl("${nickname.replace(" ", " _ ")}.jpg")

        supabase.from("users").update(
            {
                Users::image setTo avatarUrl
            }
        ) {
            filter {
                Users::nickname eq nickname
            }
        }

        Unit
    }.mapError()

    override suspend fun addUser(nickname: String): Result<Unit> = runCatching {
        val newUser = Users(
            nickname = nickname,
            uid = supabase.auth.currentUserOrNull()!!.id
        )
        supabase.from("users").insert(newUser)
        Unit
    }.mapError()
}