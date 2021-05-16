package com.skuad.talent.data.exception

import com.skuad.talent.base.entities.ResourceState


fun <T> Throwable.toResourceFailureState(code: Int) =
    when (this) {
        is RequestException -> {
            ResourceState.Failure<T>(this, this.statusCode)
        }
        else -> {
            ResourceState.Failure<T>(this, code)
        }
    }