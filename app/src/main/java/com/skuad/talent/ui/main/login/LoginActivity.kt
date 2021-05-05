package com.skuad.talent.ui.main.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.skuad.talent.databinding.ActivityLoginBinding
import com.skuad.talent.extension.setSafeOnClickListener
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.dashboard.DashboardActivity

class LoginActivity : BaseActivityVB<ActivityLoginBinding>() {
    override fun attachBinding(list: MutableList<ActivityLoginBinding>, inflater: LayoutInflater) {
        list.add(ActivityLoginBinding.inflate(layoutInflater))
    }

    override fun setup() {
        setupClickListener()
    }

    private fun setupClickListener() {
        withBinding {
            btnLogin.setSafeOnClickListener {

                startActivity(DashboardActivity.newInstance(this@LoginActivity))
            }
        }
    }

    companion object{
        fun newInstance(context: Context) = Intent(context, LoginActivity::class.java)
    }

}