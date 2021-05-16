package com.skuad.talent.base.entities.errorResponse

import com.squareup.moshi.Json

data class ApolloErrors(@Json(name = "errors") val errorList: List<ApolloError>)

data class ApolloError(
        @Json(name = "message") val message: String,
        @Json(name = "statusCode") val statusCode: Int
)