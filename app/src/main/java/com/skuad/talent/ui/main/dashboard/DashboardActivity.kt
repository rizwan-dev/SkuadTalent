package com.skuad.talent.ui.main.dashboard

import android.view.LayoutInflater
import com.skuad.talent.databinding.ActivityLoginBinding
import com.skuad.talent.databinding.DashboardBinding
import com.skuad.talent.ui.base.BaseActivityVB

class DashboardActivity : BaseActivityVB<DashboardBinding>() {
    override fun attachBinding(list: MutableList<DashboardBinding>, inflater: LayoutInflater) {
        list.add(DashboardBinding.inflate(layoutInflater))
    }

    override fun setup() {
        TODO("Not yet implemented")
    }

}