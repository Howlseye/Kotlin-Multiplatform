package com.nikola0055.kmp.network

import com.nikola0055.kmp.model.Hewan
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.http.GET
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import kotlinx.serialization.json.Json

private const val BASE_URL = "https://gh.d3ifcool.org/"

private val ktorfit = Ktorfit.Builder()
    .baseUrl(BASE_URL)
    .httpClient (
        HttpClient {
            install(ContentNegotiation) {
                register(ContentType.Any, KotlinxSerializationConverter(Json{
                    ignoreUnknownKeys = true
                }))
            }
        }
    )
    .build()

interface HewanApiService {
    @GET("hewan.php")
    suspend fun getHewan(): List<Hewan>
}

object HewanApi {
    val service: HewanApiService by lazy {
        ktorfit.createHewanApiService()
    }

    fun getHewanUrl(imageId: String): String {
        return "${BASE_URL}image.php?id=$imageId"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }