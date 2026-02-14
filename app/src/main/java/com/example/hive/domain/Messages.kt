package com.example.hive.domain

import kotlinx.serialization.Serializable

@Serializable
data class Messages(
    val id: String? = null,
    val chat_id: String,
    val sender_id: String,
    val content: String,
    val created_at: String? = null,
    val read_at: String

)