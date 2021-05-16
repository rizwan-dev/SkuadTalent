package com.skuad.talent.base.entities.errorResponse

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class DefaultErrorResponse(
    @Json(name = "error") val error: Error?,
    @Json(name = "code") val code: Int,
    @Json(name = "error_message") val errorMessage: String,
    @Json(name = "message") val message: String?
)

@Keep
data class Error(
    @Json(name = "code") val code: Int,
    @Json(name = "message") val message: String?,
    @Json(name = "status") val status: String,
    @Json(name = "type") val type: String?,
    @Json(name = "channels") val channels: List<String> = emptyList()
)