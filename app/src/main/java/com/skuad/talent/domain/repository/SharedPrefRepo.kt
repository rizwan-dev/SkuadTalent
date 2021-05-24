package com.skuad.talent.domain.repository

interface SharedPrefRepo {
    fun getAccessToken(): String
    fun setAccessToken(token: String)
    fun getAccessCookie(): String
}