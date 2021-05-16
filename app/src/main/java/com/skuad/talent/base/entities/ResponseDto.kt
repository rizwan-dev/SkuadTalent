package com.skuad.talent.base.entities

import com.squareup.moshi.Json

class ResponseDto<T>(
    @Json(name = "success") val success: Boolean,
    @Json(name = "data") val data: T,
    @Json(name = "code") val code: Int = -1,
    @Json(name = "error_message") val errorMessage: String = "",
) {
    override fun toString(): String {
        return "$success $data $code $errorMessage"
    }
}