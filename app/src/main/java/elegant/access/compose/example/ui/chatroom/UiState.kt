package elegant.access.compose.example.ui.chatroom

import elegant.access.compose.example.data.chatroom.AI_MODEL_PREFIX
import elegant.access.compose.example.data.chatroom.ChatMessage
import elegant.access.compose.example.data.chatroom.USER_PREFIX
import elegant.access.compose.example.infra.openai.Message
import kotlinx.coroutines.flow.MutableStateFlow

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

interface UiState {
    val messages: List<ChatMessage>
    val fullPromptMessage: List<Message>

    fun createLoadingMessage(): String

    fun appendMessage(id: String, text: String, done: Boolean = false)

    fun addMessage(text: String, author: String): String
}


class OpenAiUIState(
    messages: List<ChatMessage> = emptyList()
) : UiState {

    override val messages: List<ChatMessage>
        get() = this.messagesFlow.value.reversed()

    val messagesFlow = MutableStateFlow(messages)

    override val fullPromptMessage: List<Message>
        get() = messagesFlow.value.map { chatMessage ->
            Message(
                role = when (chatMessage.author) {
                    USER_PREFIX -> "user"
                    AI_MODEL_PREFIX -> "system"
                    else -> chatMessage.author
                },
                content = chatMessage.message
            )
        }

    override fun createLoadingMessage(): String {
        val chatMessage = ChatMessage(author = AI_MODEL_PREFIX, isLoading = true)
        messagesFlow.value += chatMessage
        return chatMessage.id
    }

    fun appendFirstMessage(id: String, text: String) {
        appendMessage(id, text, false)
    }

    override fun appendMessage(id: String, text: String, done: Boolean) {
        val index = messagesFlow.value.indexOfFirst { it.id == id }
        if (index != -1) {
            val newText = messagesFlow.value[index].message + text
            val updatedMessage = messagesFlow.value[index].copy(message = newText, isLoading = false)
            messagesFlow.value = messagesFlow.value.toMutableList().apply { set(index, updatedMessage) }
        }
    }

    override fun addMessage(text: String, author: String): String {
        val chatMessage = ChatMessage(
            message = text,
            author = author
        )
        messagesFlow.value += chatMessage
        return chatMessage.id
    }
}

