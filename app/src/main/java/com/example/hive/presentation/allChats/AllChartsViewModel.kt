package com.example.hive.presentation.allChats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hive.domain.ChatPreviewDto
import com.example.hive.domain.Messages
import com.example.hive.domain.ResultState
import com.example.hive.domain.useCase.MessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllChartsViewModel @Inject constructor(private val messageUseCase: MessageUseCase) :
    ViewModel() {
    private val _chats = MutableStateFlow<List<ChatPreviewDto>>(emptyList())
    val chats = _chats.asStateFlow()
    private val _stateGetChats = MutableStateFlow<ResultState>(ResultState.Idle)
    val stateGetChats = _stateGetChats.asStateFlow()

    private val _stateSubscribeToMessage = MutableStateFlow<ResultState>(ResultState.Idle)
    val stateSubscribeToMessage = _stateSubscribeToMessage.asStateFlow()
    init {
        // загружаем чаты вначале
        viewModelScope.launch {
            refreshChats()
        }

        // подписываемся на новые сообщения
        viewModelScope.launch {
            messageUseCase.subscribeToMessages(viewModelScope).collect { message ->
                // при новом сообщении заново подтягиваем список чатов
                refreshChats()
            }
        }
    }

    private suspend fun refreshChats() {
        val chats = messageUseCase().getOrElse { emptyList() }
        _chats.value = chats.sortedByDescending { it.lastMessageAt }
    }
    fun getAllChats() {
        viewModelScope.launch {
            _stateGetChats.value = ResultState.Loading

            val result = messageUseCase()

            result
                .onSuccess { chatList ->
                    _chats.value = chatList
                    _stateGetChats.value = ResultState.Success("Chats loaded")
                }
                .onFailure {
                    _stateGetChats.value =
                        ResultState.Error(it.message ?: "Ошибка")
                }
        }
    }

}