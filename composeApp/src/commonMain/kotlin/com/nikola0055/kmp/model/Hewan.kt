package com.nikola0055.kmp.model

import kotlinx.serialization.Serializable

@Serializable
data class Hewan(
    val nama: String,
    val namaLatin: String,
    val imageId: String
)
