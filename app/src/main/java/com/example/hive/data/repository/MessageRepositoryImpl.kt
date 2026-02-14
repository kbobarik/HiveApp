package com.example.hive.data.repository


import com.example.hive.domain.ChatPreviewDto
import com.example.hive.domain.Messages
import com.example.hive.domain.repository.MessageRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.RealtimeChannel
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresChangeFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject
import kotlin.collections.emptyList


class MessageRepositoryImpl @Inject constructor(
    private val supabase: SupabaseClient
) : MessageRepository {

    override suspend fun getAllChat(): Result<List<ChatPreviewDto>> {
        return try {
            val chats = supabase
                .from("chat_previews")
                .select()
                .decodeList<ChatPreviewDto>()
                .sortedByDescending { it.lastMessageAt }

            Result.success(chats)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private var channel: RealtimeChannel? = null

    override suspend fun getMessages(chatId: String): List<Messages> {
        return supabase
            .from("messages")
            .select {
                filter {
                    eq("chat_id", chatId)
                }
            }
            .decodeList()
    }

    override suspend fun subscribeToMessages(scope: CoroutineScope): Flow<Messages> {
        channel = supabase.channel("messages")

        val dataFlow = channel!!.postgresChangeFlow<PostgresAction.Insert>("public") {
            table = "messages"
        }.mapNotNull { action ->
            Json.decodeFromString<Messages>(action.record.toString())
        }

        scope.launch {
            channel!!.subscribe()
        }

        return dataFlow
    }



    override suspend fun unsubscribe(chatId: String) {
        channel?.unsubscribe()
        channel = null
    }
}



