package com.example.hive.data.local

import com.example.hive.data.repository.AllUsersRepositoryImpl
import com.example.hive.data.repository.AuthRepositoryImpl
import com.example.hive.data.repository.CreateProfileImpl
import com.example.hive.data.repository.MessageRepositoryImpl
import com.example.hive.domain.repository.AllUsersRepository
import com.example.hive.domain.repository.AuthRepository
import com.example.hive.domain.repository.CreateProfileRepository
import com.example.hive.domain.repository.MessageRepository
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

    @Binds
    @Singleton
    abstract fun bindMessageRepository(
        impl: MessageRepositoryImpl
    ): MessageRepository

    @Binds
    @Singleton
    abstract fun bindAllUsersRepository(
        impl: AllUsersRepositoryImpl
    ): AllUsersRepository



}
