package com.skuad.talent.domain.entities.login

import androidx.annotation.Keep

@Keep
data class LoginResponse(val firebaseCustomToken: String)