package com.skuad.talent.data.api

import com.skuad.talent.base.common.Constant
import retrofit2.Retrofit

class ApiManager constructor(private val retrofit: Retrofit) {
    val loginApi by lazy { retrofit.updateBaseUrl(Constant.BASES_REST_URL).createApi<LoginApi>() }
}

inline fun <reified T> Retrofit.createApi(): T = this.create(T::class.java)
fun Retrofit.updateBaseUrl(baseUrl: String): Retrofit = this.newBuilder().baseUrl(baseUrl).build()