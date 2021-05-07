package com.skuad.talent.ui.main.dashboard

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import com.skuad.talent.databinding.DashboardBinding
import com.skuad.talent.extension.setSafeOnClickListener
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.candidatelist.CandidateListActivity
import com.skuad.talent.ui.main.candidatelist.CandidateListActivity.Companion.ANDROID
import com.skuad.talent.ui.main.candidatelist.CandidateListActivity.Companion.BACKEND
import com.skuad.talent.ui.main.candidatelist.CandidateListActivity.Companion.IOS

class DashboardActivity : BaseActivityVB<DashboardBinding>() {

    override fun attachBinding(list: MutableList<DashboardBinding>, inflater: LayoutInflater) {
        list.add(DashboardBinding.inflate(layoutInflater))
    }

    override fun setup() {
        setUpClickListener()
    }

    private fun setUpClickListener() {
        withBinding {
            //for Android
            cvAndroid.setSafeOnClickListener {
                startActivity(CandidateListActivity.newInstance(this@DashboardActivity, ANDROID))
            }
            // for iOS
            cvIPhone.setSafeOnClickListener {
                startActivity(CandidateListActivity.newInstance(this@DashboardActivity, IOS))
            }
            // for Backend
            cvBackend.setSafeOnClickListener {
                startActivity(CandidateListActivity.newInstance(this@DashboardActivity, BACKEND))
            }
        }
    }

    companion object {
        fun newInstance(context: Context) = Intent(context, DashboardActivity::class.java)
    }

}