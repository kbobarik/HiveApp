package com.example.hive.data

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import jakarta.inject.Inject

class AuthRepository @Inject constructor(
    private val supabase: SupabaseClient,
    private val prefs: SharedPreferenceHelper
) {
    suspend fun signUp(email: String, password: String) {

        supabase.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }

        supabase.auth.currentAccessTokenOrNull()?.let {
            prefs.saveStringData("accessToken", it)
        }
    }

    suspend fun signIn(email: String, password: String) {
        supabase.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }

        supabase.auth.currentAccessTokenOrNull()?.let {
            prefs.saveStringData("accessToken", it)
        }
    }
}

