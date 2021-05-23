package com.skuad.talent.domain.entities.login

data class LoginRequest(val idToken: String, val contact: String = "", val redirectUrl: String = "http://hire.skuad.in/",val context: String = "firebase")