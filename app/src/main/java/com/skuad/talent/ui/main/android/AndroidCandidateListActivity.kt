package com.skuad.talent.ui.main.android

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import com.skuad.talent.databinding.ActivityAndroidCandidateListBinding
import com.skuad.talent.databinding.DashboardBinding
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.login.LoginActivity

class AndroidCandidateListActivity : BaseActivityVB<ActivityAndroidCandidateListBinding>() {

    override fun attachBinding(
        list: MutableList<ActivityAndroidCandidateListBinding>,
        inflater: LayoutInflater
    ) {
        list.add(ActivityAndroidCandidateListBinding.inflate(layoutInflater))
    }

    override fun setup() {

    }

    companion object {
        fun newInstance(context: Context) =
            Intent(context, AndroidCandidateListActivity::class.java)
    }
}