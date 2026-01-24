package com.example.hive.data

import android.util.Log
import com.example.hive.domain.Users
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import jakarta.inject.Inject

class CreateProfileRepository @Inject constructor(
    private val supabase: SupabaseClient
) {
    suspend fun addAvatar(byteArray: ByteArray, nickname: String) {
        val basket = supabase.storage["avatars"]
        basket.upload(
            "${nickname.replace(" ", "_")}.jpg",
            byteArray
        ) {
            upsert = true
        }
    }

    suspend fun checkValidNickname(nickname: String): Boolean {
        val user = supabase.from("users").select() {
            filter {
                Users::nickname eq nickname
            }
        }.decodeSingleOrNull<Users>()
        return user == null
    }

    suspend fun addUser(nickname: String) {
        Log.d("uid", supabase.auth.currentUserOrNull()?.id ?: "no")
        val avatarUrl = supabase.storage
            .from("avatars")
            .publicUrl("${nickname.replace(" ", " _ ")}.jpg")
        Log.d("avatar url", avatarUrl)
        val newUser = Users(
            image = avatarUrl,
            nickname = nickname,
            uid = supabase.auth.currentUserOrNull()!!.id
        )
        try {
            supabase.from("users").insert(newUser)
        } catch (e: Exception) {
            Log.e("SUPABASE", e.toString())
            Log.e("SUPABASE", e.message ?: "no message")
            throw e
        }
    }

}