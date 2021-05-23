package com.skuad.talent.ui.main.splash


import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import com.skuad.talent.databinding.ActivitySplashBinding
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.login.view.LoginActivity

class SplashActivity : BaseActivityVB<ActivitySplashBinding>() {
    override fun attachBinding(list: MutableList<ActivitySplashBinding>, inflater: LayoutInflater) {
        list.add(ActivitySplashBinding.inflate(inflater))
    }

    override fun setup() {
        Handler(Looper.getMainLooper()).postDelayed(::navigateToNextActivity, 1000)
    }

    private fun navigateToNextActivity() {
        startActivity(LoginActivity.newInstance(this))
        finish()
    }

}