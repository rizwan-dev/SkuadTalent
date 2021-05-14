package com.skuad.talent.data.repository

interface SharedPrefRepo {
    fun getAccessToken(): String
    fun getAccessCookie(): String
}