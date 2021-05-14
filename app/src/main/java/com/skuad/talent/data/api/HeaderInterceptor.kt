package com.skuad.talent.data.api

import androidx.annotation.NonNull
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.skuad.talent.data.repository.SharedPrefRepoImpl;
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(
    private val firebaseAuth:FirebaseAuth,
    private val prefs:SharedPrefRepoImpl
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return synchronized(this) {
            val originalRequest = chain.request()
            val requestBuilder =

                originalRequest.newBuilder()
                    .header("HEADER_AUTH", String.format("%s %s", "Bearer", getToken()))
                    .header("HEADER_APP_VERSION", "BuildConfig.appVersionName")
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