package com.example.hive.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("users")
data class Users (
    val id: String? = null,
    @SerialName("created_at")
    val created_at: String? = null,
    val image: String = "",
    val nickname: String= "",
    val uid:String? = null
)