package com.skuad.talent.domain.entities.login

import androidx.annotation.Keep

@Keep
data class GoogleRequest(
    val idToken: String = "",
    val authToken: String? = null
)