package elegant.access.compose.example.data.openai

import android.util.Log
import elegant.access.compose.example.data.system.AppConfig
import elegant.access.compose.example.infra.openai.ChatCompletionChunk
import elegant.access.compose.example.infra.openai.ChatCompletionRequest
import elegant.access.compose.example.infra.openai.ChatCompletionResult
import elegant.access.compose.example.infra.openai.Message
import elegant.access.compose.example.infra.openai.OpenAIErrorResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import java.util.concurrent.TimeoutException
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

class OpenAIRepository @Inject constructor(
    private val appConfig: AppConfig,
    private val eventSourceFactory: EventSource.Factory,
) {

    private var timeoutJob: Job? = null

    private fun CoroutineScope.resetTimeout(
        eventSource: EventSource,
        trySend: (ChatCompletionResult) -> ChannelResult<Unit>,
        close: () -> Unit
    ) {
        timeoutJob?.cancel()
        timeoutJob = launch {
            delay(30_000)
            trySend(ChatCompletionResult.Error(null, TimeoutException("Request timed out")))
            eventSource.cancel()
            close()
        }
    }

    suspend fun createChatCompletionSteaming(
        config: OpenAIConfig,
        messages: List<Message>
    ): Flow<ChatCompletionResult> = callbackFlow {
        Log.d("test", "createChatCompletionSteaming")
        val chatCompletionRequest = ChatCompletionRequest(config.modelName, messages, true)
        val jsonRequest = Json.encodeToString(chatCompletionRequest)
        val requestBody = jsonRequest.toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("${appConfig.openAIUrl}v1/chat/completions")
            .header("Authorization", "Bearer $openAIKey")
            .header("Content-Type", "application/json")
            .header("OpenAI-Beta", "assistants=v2")
            .post(requestBody)
            .build()


        val eventSourceListener = object : EventSourceListener() {
            override fun onOpen(eventSource: EventSource, response: okhttp3.Response) {
                Log.d("test", "onOpen")
                resetTimeout(eventSource, { trySend(it) }, { close() })
            }

            override fun onEvent(
                eventSource: EventSource,
                id: String?,
                type: String?,
                data: String
            ) {
                Log.d("test", "data:$data")
                resetTimeout(eventSource, { trySend(it) }, { close() })

                if (data == "[DONE]") {
                    trySendBlocking(ChatCompletionResult.Complete).isSuccess
                    close()
                    return
                }

                try {
                    val chunk = Json.decodeFromString<ChatCompletionChunk>(data)
                    trySendBlocking(ChatCompletionResult.Success(chunk)).isSuccess
                } catch (e: Exception) {
                    e.printStackTrace()
                    trySendBlocking(ChatCompletionResult.Error(null, e)).isSuccess
                    close(e)
                }
            }

            override fun onClosed(eventSource: EventSource) {
                Log.d("test", "eventSource:$eventSource")

                close()
            }

            override fun onFailure(
                eventSource: EventSource,
                t: Throwable?,
                response: okhttp3.Response?
            ) {
                val responseBodyString = response?.body?.string() ?: ""
                val errorResponse = response?.let {
                    try {
                        Json.decodeFromString<OpenAIErrorResponse>(responseBodyString)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        null
                    }
                }
                trySendBlocking(ChatCompletionResult.Error(errorResponse, t)).isSuccess
                close(t)
            }
        }

        val eventSource = eventSourceFactory.newEventSource(request, eventSourceListener)
        resetTimeout(eventSource, { trySend(it) }, { close() })

        awaitClose {
            eventSource.cancel()
            timeoutJob?.cancel()
            timeoutJob = null
        }
    }.flowOn(Dispatchers.IO)
}