package com.example.hive.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatPreviewDto(
    @SerialName("chat_id")
    val chatId: String,

    @SerialName("last_message")
    val lastMessage: String?,

    @SerialName("last_message_at")
    val lastMessageAt: String?,

    @SerialName("unread_count")
    val unreadCount: Int,

    @SerialName("user_id")
    val userId: String,

    val nickname: String,

    val image: String
)
