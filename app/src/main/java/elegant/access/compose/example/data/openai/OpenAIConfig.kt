package elegant.access.compose.example.data.openai

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

const val openAIKey = "xxxxxx-xxxxxx-xxxxxx-xxxxxx-xxxxxx-xxxxxx-xxxxxx"
const val openDoneMessage = "[DONE]"
enum class OpenAIConfig(val modelName: String) {
    GPT_3_5_TURBO_0125("gpt-3.5-turbo-0125"),
    GPT_3_5_TURBO("gpt-3.5-turbo"),
    GPT_3_5_TURBO_1106("gpt-3.5-turbo-1106"),
    GPT_3_5_TURBO_INSTRUCT("gpt-3.5-turbo-instruct"),
    GPT_4_TURBO_2024_04_09("gpt-4-turbo-2024-04-09"),
    GPT_4_TURBO_PREVIEW("gpt-4-turbo-preview"),
    GPT_4_0125_PREVIEW("gpt-4-0125-preview"),
    GPT_4_1106_PREVIEW("gpt-4-1106-preview"),
    GPT_4("gpt-4"),
    GPT_4_0613("gpt-4-0613"),
    GPT_4_0314("gpt-4-0314"),
    DALL_E_3("dall-e-3"),
    DALL_E_2("dall-e-2"),
    TTS_1("tts-1"),
    TTS_1_HD("tts-1-hd"),
    WHISPER_1("whisper-1"),
    TEXT_EMBEDDING_3_LARGE("text-embedding-3-large"),
    TEXT_EMBEDDING_3_SMALL("text-embedding-3-small"),
    TEXT_EMBEDDING_ADA_002("text-embedding-ada-002");
}

