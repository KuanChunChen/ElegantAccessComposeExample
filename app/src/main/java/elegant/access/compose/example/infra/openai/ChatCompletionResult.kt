package elegant.access.compose.example.infra.openai

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
sealed class ChatCompletionResult {
    data class Success(val chunk: ChatCompletionChunk) : ChatCompletionResult()
    data class Error(val errorResponse: OpenAIErrorResponse?, val throwable: Throwable?) : ChatCompletionResult()
    data object Complete : ChatCompletionResult()
}