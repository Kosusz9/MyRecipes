package org.example.myrecipes.ai

import io.ktor.client.*
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.*
import kotlinx.serialization.json.*

class ChatAIClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    private val apiKey = "YOUR_API_KEY"

    suspend fun getRecipeSuggestion(query: String): String {
        return try {
            val response: HttpResponse = client.post("https://api.groq.com/openai/v1/chat/completions") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $apiKey")
                    contentType(ContentType.Application.Json)
                }
                setBody(
                    CompletionRequest(
                        messages = listOf(
                            CompletionRequest.Message(
                                role = "user",
                                content = "Podaj mi przepis na $query"
                            )
                        ),
                        model = "gemma2-9b-it"
                    )
                )
            }

            when (response.status) { //401, 403, 429, 500, 502, 503
                HttpStatusCode.Unauthorized, HttpStatusCode.Forbidden, HttpStatusCode.TooManyRequests,
                HttpStatusCode.InternalServerError, HttpStatusCode.BadGateway, HttpStatusCode.ServiceUnavailable, -> {
                    val errorResponse = response.bodyAsText()
                    val errorMessage = try {
                        val error = Json.decodeFromString<ErrorResponse>(errorResponse)
                        error.error.message
                    } catch (e: SerializationException) {
                        "Błąd autoryzacji: ${response.status.description}"
                    }
                    "Błąd: $errorMessage"
                } else -> {
                    val responseBody = response.bodyAsText()
                    try {
                        val completionResponse = Json.decodeFromString<CompletionResponse>(responseBody)
                        completionResponse.choices.firstOrNull()?.message?.content ?: "Brak odpowiedzi"
                    } catch (e: SerializationException) {
                        "Błąd: parsing response: ${e.message}"
                    }
                }
            }
        } catch (e: SocketTimeoutException) {
            "Błąd: Upłynął limit czasu żądania."
        } catch (e: IOException) {
            "Błąd: Brak połączenia z Internetem."
        } catch (e: Exception) {
            "Nieoczekiwany błąd: ${e.message}"
        }
    }
}

@Serializable
data class CompletionRequest(
    val messages: List<Message>,
    val model: String
) {
    @Serializable
    data class Message(
        val role: String,
        val content: String
    )
}

@Serializable
data class CompletionResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val choices: List<Choice>,
    val usage: Usage,
    val system_fingerprint: String,
    val x_groq: XGroq
) {
    @Serializable
    data class Choice(
        val index: Int,
        val message: Message,
        val logprobs: JsonNull? = null,
        val finish_reason: String
    )

    @Serializable
    data class Message(
        val role: String,
        val content: String
    )

    @Serializable
    data class Usage(
        val queue_time: Double,
        val prompt_tokens: Int,
        val prompt_time: Double,
        val completion_tokens: Int,
        val completion_time: Double,
        val total_tokens: Int,
        val total_time: Double
    )
    @Serializable
    data class XGroq(
        val id: String
    )
}

@Serializable
data class ErrorResponse(
    val error: ErrorDetail
) {
    @Serializable
    data class ErrorDetail(
        val message: String,
        val type: String,
        val code: String
    )
}