package elegant.access.compose.example.infra.openai

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

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

@Serializable
data class OpenAIErrorResponse(
    val error: Error? = null,
)

@Serializable
data class Error(
    val code: String? = null,
    val message: String? = null,
    val `param`: JsonElement? = null,
    val type: String? = null,
)