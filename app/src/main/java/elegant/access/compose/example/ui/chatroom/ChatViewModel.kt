package elegant.access.compose.example.ui.chatroom

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import elegant.access.compose.example.data.chatroom.Author
import elegant.access.compose.example.data.chatroom.ChatMessage
import elegant.access.compose.example.data.openai.OpenAIConfig
import elegant.access.compose.example.data.openai.OpenAIRepository
import elegant.access.compose.example.infra.openai.ChatCompletionResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This file is part of an Android project developed by elegant.access.
 *
 * For more information about this project, you can visit our website:
 * {@link https://elegantaccess.org}
 *
 * Please note that this project is for educational purposes only and is not intended
 * for use in production environments.
 *
 * @author Willy.Chen
 * @version 1.0.0
 * @since 2020~2024
 */

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class ChatViewModel @Inject constructor(
    private val openAIRepository: OpenAIRepository
) : ViewModel() {


    private val uiState: MutableStateFlow<OpenAiUIState> = MutableStateFlow(OpenAiUIState())

    private val textInputEnabledFlow: MutableStateFlow<Boolean> =
        MutableStateFlow(true)

    data class ViewState(
        val messages: List<ChatMessage>,
        val textInputEnabled: Boolean,
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val viewStateFlow = combine(
        uiState.flatMapLatest { it.messagesFlow },
        textInputEnabledFlow
    ) { messages, textInputEnabled ->

        ViewState(
            messages.reversed(),
            textInputEnabled
        )
    }.stateIn(
        viewModelScope, SharingStarted.Eagerly, ViewState(
            emptyList(),
            true,
        )
    )

    fun startSentMessage(userMessage: String) {
        viewModelScope.launch {
            uiState.value.addMessage(userMessage, Author.User)
            val currentMessageId: String = uiState.value.createLoadingMessage()
            textInputEnabledFlow.emit(false)
            try {
                val fullPromptMessage = uiState.value.fullPromptMessage
                openAIRepository.createChatCompletionSteaming(
                    OpenAIConfig.GPT_3_5_TURBO,
                    fullPromptMessage
                ).collectIndexed { index, result ->

                    delay(100)
                    currentMessageId.let {
                        when (result) {
                            is ChatCompletionResult.Success -> {
                                val responseMessage =
                                    result.chunk.choices?.firstOrNull()?.delta?.content ?: ""
                                if (index == 0) {
                                    uiState.value.appendFirstMessage(it, responseMessage)
                                } else {
                                    uiState.value.appendMessage(it, responseMessage, true)
                                }
                            }

                            is ChatCompletionResult.Error -> {
                                val errorMessage = result.errorResponse?.error?.message
                                    ?: result.throwable?.message ?: "Unknown Error"

                                if (index == 0) {
                                    uiState.value.appendFirstMessage(it, errorMessage,Author.SystemError )
                                } else {
                                    uiState.value.appendMessage(it, errorMessage, true, Author.SystemError)
                                }
                                textInputEnabledFlow.emit(true)
                            }

                            ChatCompletionResult.Complete -> {
                                textInputEnabledFlow.emit(true)
                            }
                        }
                    }

                }
            } catch (e: Exception) {
                uiState.value.addMessage(e.localizedMessage ?: "Unknown Error", Author.SystemError)
                textInputEnabledFlow.emit(true)
            }
        }
    }
}