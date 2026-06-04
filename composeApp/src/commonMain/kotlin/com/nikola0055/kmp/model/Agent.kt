package com.nikola0055.kmp.model

import kotlinx.serialization.Serializable

@Serializable
data class Agent(
    val name: String,
    val ultimate: String,
    val img: String
)
