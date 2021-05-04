package com.skuad.talent

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class SkuadApp : Application(){

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}