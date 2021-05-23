package com.skuad.talent.data.api

import androidx.annotation.NonNull
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.skuad.talent.data.repository.SharedPrefRepoImpl
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor (
    private val firebaseAuth:FirebaseAuth,
    private val prefs: SharedPrefRepoImpl
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        getToken()
        return synchronized(this) {
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                .header("Cookie", COOKIE)
            chain.proceed(requestBuilder.build())
        }
    }

    @NonNull
    private fun getToken() = try {
        firebaseAuth.currentUser?.getIdToken(false)?.let {
            (Tasks.await(it).token)?.also { tokenValue ->
                prefs.setAccessToken(tokenValue)
            }
        } ?: prefs.getAccessToken()
    } catch (exception: Exception) {
        exception.printStackTrace()
        FirebaseCrashlytics.getInstance().recordException(exception)
        prefs.getAccessToken()
    }

}

const val COOKIE = "skuad-token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjU0OGU3Njc3LTMwZGQtNDM5YS05MWU5LWM3ZGFjZDk2MmRjZiIsIm5hbWUiOiJUZXN0IFNhbml0eSIsImVtYWlscyI6W3siX2lkIjoiNjA5YjY0OTFiYTc3NjEwMDE3ZWMxNjI3IiwiZW1haWwiOiJzYW5pdHkuemVucHJvQGdtYWlsLmNvbSIsInZlcmlmaWVkIjp0cnVlLCJzb3VyY2UiOiJnb29nbGUiLCJwcmltYXJ5Ijp0cnVlfV0sImNvbnRhY3RzIjpbeyJfaWQiOiI2MDliNjQ5MWJhNzc2MTAwMTdlYzE2MjgiLCJtb2JpbGUiOiIrOTE4ODMwNzIxMzM0IiwidmVyaWZpZWQiOmZhbHNlLCJwcmltYXJ5Ijp0cnVlfV0sInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQVRYQUp3RUZOdXZPSjE4NEFLTXpnTkYxRFkwSk1xemVzc2dOZ25YdkREUD1zOTYtYyIsImZpcnN0TmFtZSI6IlRlc3QiLCJsYXN0TmFtZSI6IlNhbml0eSIsImlhdCI6MTYyMDc5NjU2MX0.RkVm4kh-XQz4N7U4KYl240qD3VbFB8mlAaX4o7gyY9g-8FyiYe2IGnwoebC1QtQVyaytaoeCWo3ap2VOdF3IXWkihS9Q041EpCFOYAKotwRMGaQOjWqwyNMyip1yu71d0yXAe5aYlUPSfv-8GehvUSsLneAaXeU7CIawaFptnKU"