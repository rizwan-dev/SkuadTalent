package com.skuad.talent.data.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.skuad.talent.data.base.*

class SharedPrefRepoImpl(private val context: Context) : SharedPrefRepo, BaseRepo {

    companion object {
        private const val TALENT_PREF_NAME = "TalentPrefs"
        private const val ACCESS_TOKEN_PREF = "accessToken"
        private const val AUTH_TOKEN_PREF = "authToken"
        private const val ACCESS_COOKIE_PREF = "accessCookie"
    }

    private val prefs by lazy {
        context.getSharedPreferences(TALENT_PREF_NAME, MODE_PRIVATE)
    }

    fun clearAllData() {
        prefs.edit().clear().apply()
    }

    ///  Used to send in header with api
    fun setAccessToken(value: String) {
        prefs.edit().putString(ACCESS_TOKEN_PREF, value).apply()
    }

    override fun getAccessToken(): String {
        return prefs.getString(ACCESS_TOKEN_PREF, "") ?: ""
    }

    fun setAccessCookie(value: String) {
        prefs.edit().putString(ACCESS_COOKIE_PREF, value).apply()
    }

    override fun getAccessCookie(): String {
        return prefs.getString(ACCESS_COOKIE_PREF, "") ?: ""
    }

    fun setAuthToken(value: String) {
        prefs.edit().putString(AUTH_TOKEN_PREF, value).apply()
    }

    fun getAuthToken(): String {
        return prefs.getString(AUTH_TOKEN_PREF, "") ?: ""
    }


}