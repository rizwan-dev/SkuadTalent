package com.skuad.talent.ui.main.ios

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import com.skuad.talent.databinding.ActivityAndroidCandidateListBinding
import com.skuad.talent.databinding.ActivityIosCandidateListBinding
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.android.AndroidCandidateListActivity

class IosCandidateListActivity : BaseActivityVB<ActivityIosCandidateListBinding>() {

    override fun attachBinding(
        list: MutableList<ActivityIosCandidateListBinding>,
        inflater: LayoutInflater
    ) {
        list.add(ActivityIosCandidateListBinding.inflate(layoutInflater))
    }

    override fun setup() {

    }
    companion object {
        fun newInstance(context: Context) =
            Intent(context, IosCandidateListActivity::class.java)
    }
}