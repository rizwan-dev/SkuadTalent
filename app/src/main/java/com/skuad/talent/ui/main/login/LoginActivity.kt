package com.skuad.talent.ui.main.login

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import com.skuad.talent.databinding.NewActivityLoginBinding
import com.skuad.talent.extension.setSafeOnClickListener
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.dashboard.view.NewDashboardActivity

class LoginActivity : BaseActivityVB<NewActivityLoginBinding>() {
    override fun attachBinding(list: MutableList<NewActivityLoginBinding>, inflater: LayoutInflater) {
        list.add(NewActivityLoginBinding.inflate(layoutInflater))
    }

    override fun setup() {
        setupClickListener()
    }

    private fun setupClickListener() {
        withBinding {
            btnLogin.setSafeOnClickListener {

                startActivity(NewDashboardActivity.newInstance(this@LoginActivity))
            }
            tvSignupInstruction.setSafeOnClickListener {
                startActivity(SignUpActivity.newInstance(this@LoginActivity))
            }
        }
    }

    companion object{
        fun newInstance(context: Context) = Intent(context, LoginActivity::class.java)
    }

}