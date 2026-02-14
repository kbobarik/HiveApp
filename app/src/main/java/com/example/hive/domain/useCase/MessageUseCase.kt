package com.example.hive.domain.useCase

import com.example.hive.domain.ChatPreviewDto
import com.example.hive.domain.Messages
import com.example.hive.domain.repository.MessageRepository
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class MessageUseCase @Inject constructor(private val messageRepository: MessageRepository) {
    suspend operator fun invoke(): Result<List<ChatPreviewDto>> {
       return messageRepository.getAllChat()
    }

    suspend fun  subscribeToMessages(
        scope: CoroutineScope
    ): Flow<Messages> {
        return messageRepository.subscribeToMessages(scope)
    }
}