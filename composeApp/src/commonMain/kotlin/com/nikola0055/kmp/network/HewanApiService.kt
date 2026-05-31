package com.nikola0055.kmp.network

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/" + "indraazimi/mobpro1-compose/static-api/"

private val ktorfit = Ktorfit.Builder()
    .baseUrl(BASE_URL)
    .build()

interface HewanApiService {
    @GET("static-api.json")
    suspend fun getHewan(): String
}

object HewanApi {
    val service: HewanApiService by lazy {
        ktorfit.createHewanApiService()
    }
}
