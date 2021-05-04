package com.skuad.talent.ui.main.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.skuad.talent.databinding.ActivityLoginBinding
import com.skuad.talent.ui.base.BaseActivityVB

class LoginActivity : BaseActivityVB<ActivityLoginBinding>() {
    override fun attachBinding(list: MutableList<ActivityLoginBinding>, inflater: LayoutInflater) {
        list.add(ActivityLoginBinding.inflate(layoutInflater))
    }

    override fun setup() {

    }

}