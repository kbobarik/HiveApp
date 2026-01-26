package com.example.hive.data.local

import com.example.hive.data.repository.AuthRepositoryImpl
import com.example.hive.data.repository.CreateProfileImpl
import com.example.hive.domain.repository.AuthRepository
import com.example.hive.domain.repository.CreateProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindCreateProfileRepository(
        impl: CreateProfileImpl
    ): CreateProfileRepository


}
