package com.example.hive.domain

import kotlinx.serialization.Serializable

@Serializable
data class Chats (
    val id: String? = null,
    val created_at: String? = null,
    val last_message_at: String
)