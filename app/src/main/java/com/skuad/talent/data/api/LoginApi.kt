package com.skuad.talent.data.api

import com.skuad.talent.base.common.Constant
import com.skuad.talent.domain.entities.ResponseDto
import com.skuad.talent.domain.entities.login.LoginRequest
import com.skuad.talent.domain.entities.login.LoginResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginApi {
    @POST("auth/hire")
    @Headers("$HEADER_AUTH:${Constant.BASIC_AUTH}")
    fun loginAsync(@Body loginRequest: LoginRequest): Call<LoginResponseData>
}

const val HEADER_AUTH = "Authorization"
const val HEADER_SKIP_ALL = "x-skip-all"
const val CONTENT_TYPE = "Content-Type"
const val CONTENT_TYPE_JSON = "application/json"
const val HEADER_OS_VALUE = "android"

