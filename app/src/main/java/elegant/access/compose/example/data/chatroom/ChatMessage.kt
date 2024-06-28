package elegant.access.compose.example.data.chatroom

import java.util.UUID

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
data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val message: String = "",
    val author: String,
    val isLoading: Boolean = false
) {
    val isFromUser: Boolean
        get() = author == USER_PREFIX
}

const val USER_PREFIX = "user"
const val AI_MODEL_PREFIX = "system"

