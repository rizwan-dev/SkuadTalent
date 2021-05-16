package com.skuad.talent.domain.repository

interface SharedPrefRepo {
    fun getAccessToken(): String
    fun getAccessCookie(): String
}