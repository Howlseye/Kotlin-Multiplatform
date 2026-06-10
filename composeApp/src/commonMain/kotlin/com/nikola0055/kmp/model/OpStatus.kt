package com.nikola0055.kmp.model

import kotlinx.serialization.Serializable

@Serializable
data class OpStatus(
    var status: String,
    var message: String? = null
)
