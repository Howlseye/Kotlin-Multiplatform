package com.nikola0055.kmp.network

import com.nikola0055.kmp.model.Agent
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.http.GET
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import kotlinx.serialization.json.Json

private const val BASE_URL = "https://www.jsonkeeper.com/b/"

private val ktorfit = Ktorfit.Builder()
    .baseUrl(BASE_URL)
    .httpClient (
        HttpClient {
            install(ContentNegotiation) {
                register(ContentType.Any, KotlinxSerializationConverter(Json))
            }
        }
    )
    .build()

interface AgentApiService {
    @GET("VTGT3")
    suspend fun getAgent(): List<Agent>
}

object AgentApi {
    val service: AgentApiService by lazy {
        ktorfit.createAgentApiService()
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }