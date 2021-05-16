package com.skuad.talent.base.extensions

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.skuad.talent.base.entities.ResourceState
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import com.skuad.talent.base.common.AppErrorCodes


suspend fun <F, T> ApolloCall<F>.mapToEntity(
    requestTag: String = "", mapper: (F) -> T
): ResourceState<T> {
    return try {
        val response: Response<F> = this.toDeferredAsync().await()
        val data = response.data
        if (response.hasErrors()) {
            val errorData = response.errors?.getOrNull(0)
            return when(val errorCode =
                    errorData?.customAttributes?.get("statusCode")?.toString()?.toInt()) {

                AppErrorCodes.FORBIDDEN.code ->
                    ResourceState.Failure(
                            Exception(errorData.message),
                            code = errorCode,
                            requestTag = requestTag,
                            body = data?.let { mapper(it) }
                    )


                else -> ResourceState.Failure(
                    Exception(errorData?.message),
                    code = errorCode ?: 500,
                    requestTag = requestTag,
                )
            }
        }

        return ResourceState.Success(mapper(data!!), AppErrorCodes.SUCCESS.code)
    } catch (e: Exception) {
        e.printStackTrace()
        e.toResourceFailureState(requestTag)
    }
}

fun <T> ApolloCall<T>.toDeferredAsync(): Deferred<Response<T>> {
    val deferred = CompletableDeferred<Response<T>>()

    deferred.invokeOnCompletion {
        if (deferred.isCancelled) {
            cancel()
        }
    }
    enqueue(object : ApolloCall.Callback<T>() {
        override fun onResponse(response: Response<T>) {
            if (deferred.isActive) {
                deferred.complete(response)
            }
        }

        override fun onHttpError(e: ApolloHttpException) {
            if (deferred.isActive) {
                deferred.completeExceptionally(e)
            }
        }

        override fun onFailure(e: ApolloException) {
            if (deferred.isActive) {
                deferred.completeExceptionally(e)
            }
        }
    })

    return deferred
}