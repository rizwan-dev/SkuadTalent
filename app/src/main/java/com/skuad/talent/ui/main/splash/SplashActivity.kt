package com.skuad.talent.ui.main.splash


import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import com.skuad.talent.databinding.ActivitySplashBinding
import com.skuad.talent.domain.repository.SharedPrefRepo
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.dashboard.view.DashboardActivity
import com.skuad.talent.ui.main.login.view.LoginActivity
import javax.inject.Inject

class SplashActivity : BaseActivityVB<ActivitySplashBinding>() {

    @Inject
    lateinit var sharedPrefRepo: SharedPrefRepo

    override fun attachBinding(list: MutableList<ActivitySplashBinding>, inflater: LayoutInflater) {
        list.add(ActivitySplashBinding.inflate(inflater))
    }

    override fun setup() {
        Handler(Looper.getMainLooper()).postDelayed(::navigateToNextActivity, 1500)
    }

    private fun navigateToNextActivity() {
        if(sharedPrefRepo.getAccessToken().isNullOrEmpty()){
            startActivity(LoginActivity.newInstance(this))
        } else{
            startActivity(DashboardActivity.newInstance(this))
        }
        finish()
    }

}