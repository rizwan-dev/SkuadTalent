package com.skuad.talent.domain.repository

import com.skuad.talent.base.BaseRepo
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.domain.entities.login.GoogleRequest
import com.skuad.talent.domain.entities.login.LoginResponse
import com.skuad.talent.domain.entities.login.LoginResponseData
import com.skuad.talent.domain.entities.login.SocialLoginResponse

interface LoginRepo : BaseRepo {
    suspend fun loginFirebaseAsync(request: String): ResourceState<LoginResponseData>
    suspend fun googleSignInAsync(request: GoogleRequest): ResourceState<SocialLoginResponse>
}