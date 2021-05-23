package com.skuad.talent.data.api

import retrofit2.Retrofit

class ApiManager constructor(private val retrofit: Retrofit) {
    val loginApi by lazy { retrofit.updateBaseUrl("https://id-service-skd.herokuapp.com/").createApi<LoginApi>() }
}

inline fun <reified T> Retrofit.createApi(): T = this.create(T::class.java)
fun Retrofit.updateBaseUrl(baseUrl: String): Retrofit = this.newBuilder().baseUrl(baseUrl).build()