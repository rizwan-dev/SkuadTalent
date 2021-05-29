package com.skuad.talent.ui.main.login

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import com.skuad.talent.databinding.ActivitySignUpBinding
import com.skuad.talent.extension.setSafeOnClickListener
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.dashboard.view.DashboardActivity
import com.skuad.talent.ui.main.login.view.LoginActivity

class SignUpActivity : BaseActivityVB<ActivitySignUpBinding>() {
    override fun attachBinding(list: MutableList<ActivitySignUpBinding>, inflater: LayoutInflater) {
        list.add(ActivitySignUpBinding.inflate(layoutInflater))
    }

    override fun setup() {
        setupClickListener()
    }

    private fun setupClickListener() {
        withBinding {
            btnCreateAccount.setSafeOnClickListener {
                startActivity(DashboardActivity.newInstance(this@SignUpActivity))
            }

            tvAlreadySigup.setSafeOnClickListener {
                startActivity(LoginActivity.newInstance(this@SignUpActivity))
            }

        }
    }

    companion object {
        fun newInstance(context: Context) = Intent(context, SignUpActivity::class.java)
    }
}