package com.example.hive.domain.repository

import com.example.hive.domain.ChatPreviewDto
import com.example.hive.domain.Messages
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun getAllChat(): Result<List<ChatPreviewDto>>
    suspend fun getMessages(chatId: String): List<Messages>
    suspend fun subscribeToMessages(scope: CoroutineScope): Flow<Messages>

    suspend fun unsubscribe(chatId: String)
}