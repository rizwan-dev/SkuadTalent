package com.skuad.talent.data.exception

import androidx.annotation.Keep

@Keep
data class RequestException(
    val statusCode: Int,
    val title: String?,
    val channels: List<String>? = mutableListOf()
) : Exception()