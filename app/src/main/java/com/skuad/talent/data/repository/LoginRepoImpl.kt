package com.skuad.talent.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.base.extensions.firebaseException
import com.skuad.talent.base.extensions.mapToEntity
import com.skuad.talent.data.api.LoginApi
import com.skuad.talent.domain.entities.login.*
import com.skuad.talent.domain.repository.LoginRepo
import net.zenpro.data.extensions.await

class LoginRepoImpl(
    private val firebaseAuth: FirebaseAuth,
    private val prefs: SharedPrefRepoImpl,
    private val loginApi: LoginApi,
): LoginRepo {

    override suspend fun loginFirebaseAsync(request: String): ResourceState<LoginResponseData> {
        return loginApi.loginAsync(LoginRequest(idToken = request)).mapToEntity(true){
            it
        }
    }

    override suspend fun googleSignInAsync(request: GoogleRequest): ResourceState<SocialLoginResponse> {
        val credential = GoogleAuthProvider.getCredential(request.idToken, request.authToken)
        return try {
            firebaseAuth.signInWithCredential(credential).await()
            firebaseAuth.currentUser?.run {
                val firebaseToken = this.getIdToken(true).await().token ?: ""
                ResourceState.Success(SocialLoginResponse("ok", firebaseToken), 200)
            } ?: ResourceState.Failure(FirebaseAuthException("Error", "Google error"))
        } catch (exception: Exception) {
            FirebaseCrashlytics.getInstance().recordException(exception)
            exception.firebaseException()
        }
    }
}