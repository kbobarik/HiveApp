package com.example.hive.data.supabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.PropertyConversionMethod
import io.github.jan.supabase.storage.Storage
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient =
        createSupabaseClient(
            supabaseUrl = "https://vvbrotfgqiqdlxiykrmn.supabase.co ",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZ2YnJvdGZncWlxZGx4aXlrcm1uIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTgyMDI3NTgsImV4cCI6MjA3Mzc3ODc1OH0.vVwYkG-ERCyx4AegyM5kaqGuFX_bXwO9NGSePceesw0"
        ) {
            install(Auth.Companion)
            install(Postgrest.Companion) {
                propertyConversionMethod = PropertyConversionMethod.Companion.SERIAL_NAME
            }
            install(Storage.Companion)
        }
}