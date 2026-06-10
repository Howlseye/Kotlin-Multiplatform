package com.nikola0055.kmp.network

import com.nikola0055.kmp.model.Hewan
import com.nikola0055.kmp.model.OpStatus
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Header
import de.jensklingenberg.ktorfit.http.POST
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.MultiPartFormDataContent
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

    @POST("hewan.php")
    suspend fun postHewan(
        @Header("Authorization") userId: String,
        @Body content: MultiPartFormDataContent
    ): OpStatus
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