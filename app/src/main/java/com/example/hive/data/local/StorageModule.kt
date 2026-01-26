package com.example.hive.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideSharedPreferenceHelper(
        prefs: SharedPreferences
    ): SharedPreferenceHelper = SharedPreferenceHelper(prefs)
}