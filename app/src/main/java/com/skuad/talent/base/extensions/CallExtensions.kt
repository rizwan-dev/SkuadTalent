package com.skuad.talent.base.extensions

import android.annotation.SuppressLint
import android.util.Log
import com.skuad.talent.base.common.AppErrorCodes
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.base.entities.ResponseDto
import com.skuad.talent.data.exception.RequestException
import com.skuad.talent.base.entities.errorResponse.DefaultErrorResponse
import okhttp3.ResponseBody
import retrofit2.Call

fun <F, T> Call<F>.mapToEntity(
    isRestCall: Boolean = false, mapper: (F) -> T
): ResourceState<T> {
    return try {
        val response = this.execute()

        when {
            response.isSuccessful -> {
                val body = response.body()
                val headers = response.headers()
                val token = headers["skuad-token"]

                if (body == null || (body is ResponseDto<*> && (body as? ResponseDto<*>)?.success == false)) {
                    val responseBody = (body as? ResponseDto<*>)

                    if (responseBody?.errorMessage.isNullOrBlank())
                        ResourceState.Failure(java.lang.Exception("something went wrong"))
                    else {
                        ResourceState.Failure(
                            Exception(responseBody?.errorMessage),
                            code = responseBody?.code ?: AppErrorCodes.FAILURE.code)
                    }
                } else {
                    @Suppress("UNCHECKED_CAST")
                    ResourceState.Success(mapper(body as F), response.code())
                }
            }


            response.code() == AppErrorCodes.FORBIDDEN.code -> {
                ResourceState.Failure(
                        Exception(response.message()),
                        code = response.code()
                )
            }

            else -> {
                parseError(response.errorBody())?.let {
                    val error = it.error

                    if (isRestCall) {
                        if (it.errorMessage.isBlank() && error != null) {
                            ResourceState.Failure<T>(
                                    RequestException(error.code, error.message)
                            )
                        } else if (it.errorMessage.isNotBlank()) {
                            ResourceState.Failure(
                                    RequestException(response.code(), it.errorMessage)
                            )
                        } else {
                            ResourceState.Failure(java.lang.Exception("Something went wrong"))
                        }
                    } else {
                        ResourceState.Failure(
                                RequestException(
                                        response.code(),
                                        error?.message ?: "something went wrong"
                                )
                        )
                    }
                } ?: kotlin.run {
                    ResourceState.Failure<T>(RequestException(response.code(), null))
                }
            }
        }
    } catch (exception: Exception) {
        exception.printStackTrace()
        exception.toResourceFailureState()
    }
}

@SuppressLint("DefaultLocale")
fun <F, T> Call<F>.s3CallToExtension(
    mapper: (Boolean) -> T
): ResourceState<T> {
    return try {
        val response = this.execute()
        if (response.isSuccessful) {
            ResourceState.Success(mapper(true), response.code())
        } else {
            return ResourceState.Failure(RequestException(response.code(), null))
        }
    } catch (exception: Exception) {
        exception.toResourceFailureState()
    }
}

private fun parseError(errorBody: ResponseBody?): DefaultErrorResponse? {
    return errorBody?.source()?.readUtf8()?.run {
        Log.e("", this)
        val jsonAdapter = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter(DefaultErrorResponse::class.java)
        return try {
            jsonAdapter.fromJson(this)
        } catch (exception: Exception) {
            null
        }
    } ?: run {
        null
    }
}