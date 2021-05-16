package com.skuad.talent.base.extensions

import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.exception.ApolloNetworkException
import com.skuad.talent.base.common.AppErrorCodes
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.base.entities.errorResponse.ApolloErrors
import com.skuad.talent.data.exception.RequestException
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

fun <T> Exception.toResourceFailureState(requestTag: String = "") =
    when (this) {
        is ApolloNetworkException -> ResourceState.Failure<T>(
            this,
            AppErrorCodes.NO_INTERNET_CONNECTION.code,
            requestTag
        )
        is ApolloHttpException -> {
            rawResponse()?.body?.string()?.run {
                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val jsonAdapter = moshi.adapter(ApolloErrors::class.java)
                val errorResponse = try {
                    jsonAdapter.fromJson(this)?.errorList?.getOrNull(0)
                } catch (e: Exception) {
                    null
                }
                when (errorResponse?.statusCode) {
                    else -> ResourceState.Failure<T>(
                        RequestException(
                            errorResponse?.statusCode ?: AppErrorCodes.FAILURE.code,
                            errorResponse?.message ?: "Something went wrong"
                        ), requestTag = requestTag
                    )
                }
            } ?: ResourceState.Failure<T>(this, requestTag = requestTag)
        }
        is UnknownHostException,
        is SocketException,
        is SocketTimeoutException,
        is TimeoutException,
        is ConnectException -> ResourceState.Failure<T>(
            this,
            AppErrorCodes.NO_INTERNET_CONNECTION.code,
            requestTag
        )
        is JSONException -> ResourceState.Failure<T>(
            RequestException(
                AppErrorCodes.FAILURE.code,
                "Something went wrong"
            ), requestTag = requestTag
        )
        else -> {
            ResourceState.Failure(this, requestTag = requestTag)
        }
    }

fun <T> Exception.firebaseException() = ResourceState.Failure<T>(this)