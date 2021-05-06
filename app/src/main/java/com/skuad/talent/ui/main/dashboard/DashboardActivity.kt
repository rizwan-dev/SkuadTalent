package com.skuad.talent.ui.main.dashboard

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import com.skuad.talent.databinding.ActivityLoginBinding
import com.skuad.talent.databinding.DashboardBinding
import com.skuad.talent.extension.setSafeOnClickListener
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.android.AndroidCandidateListActivity
import com.skuad.talent.ui.main.backend.BackendCandidateListActivity
import com.skuad.talent.ui.main.ios.IosCandidateListActivity
import com.skuad.talent.ui.main.login.LoginActivity

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
                startActivity(AndroidCandidateListActivity.newInstance(this@DashboardActivity))
            }
            // for iOS
            cvIPhone.setSafeOnClickListener {
                startActivity(IosCandidateListActivity.newInstance(this@DashboardActivity))
            }
            // for Backend
            cvBackend.setSafeOnClickListener {
                startActivity(BackendCandidateListActivity.newInstance(this@DashboardActivity))
            }
        }
    }

    companion object {
        fun newInstance(context: Context) = Intent(context, DashboardActivity::class.java)
    }

}