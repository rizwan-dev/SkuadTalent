package com.skuad.talent.ui.main.dashboard

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.skuad.talent.databinding.DashboardBinding
import com.skuad.talent.databinding.NewDashboardActivityBinding
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.candidatelist.CandidateListActivity
import com.skuad.talent.utils.DataUtil

class NewDashboardActivity : BaseActivityVB<NewDashboardActivityBinding>() {

    override fun attachBinding(
        list: MutableList<NewDashboardActivityBinding>,
        inflater: LayoutInflater
    ) {
        list.add(NewDashboardActivityBinding.inflate(layoutInflater))
    }

    override fun setup() {
        setUpView()
    }

    private fun setUpView() {
        withBinding {
            val cardList = DataUtil.getCardsList()
            rvDashboard.layoutManager = GridLayoutManager(this@NewDashboardActivity, 2)
            rvDashboard.adapter = DashboardAdapter(this@NewDashboardActivity, cardList) {
                startActivity(
                    CandidateListActivity.newInstance(this@NewDashboardActivity,
                        CandidateListActivity.ANDROID
                    ))
            }
        }
    }

    companion object {
        fun newInstance(context: Context) = Intent(context, NewDashboardActivity::class.java)
    }
}