package com.skuad.talent.ui.main.backend

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import com.skuad.talent.databinding.ActivityBackendCandidateListBinding
import com.skuad.talent.databinding.ActivityIosCandidateListBinding
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.android.AndroidCandidateListActivity

class BackendCandidateListActivity : BaseActivityVB<ActivityBackendCandidateListBinding>() {

    override fun attachBinding(
        list: MutableList<ActivityBackendCandidateListBinding>,
        inflater: LayoutInflater
    ) {
        list.add(ActivityBackendCandidateListBinding.inflate(layoutInflater))
    }

    override fun setup() {

    }
    companion object {
        fun newInstance(context: Context) =
            Intent(context, BackendCandidateListActivity::class.java)
    }
}