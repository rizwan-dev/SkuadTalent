package com.skuad.talent.domain.entities.login

import androidx.annotation.Keep

@Keep
data class SocialLoginResponse(
    val status: String,
    val authToken: String = ""
)