package com.skuad.talent.ui.main.candidatelist.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.skuad.talent.R
import com.skuad.talent.databinding.ActivityCandidateListBinding
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.candiatedetails.CandidateDetailActivity
import com.skuad.talent.ui.main.candidatelist.adapter.CandidateListAdapter
import com.skuad.talent.utils.DataUtil


class CandidateListActivity : BaseActivityVB<ActivityCandidateListBinding>() {

    private lateinit var skillName: String

    override fun attachBinding(
        list: MutableList<ActivityCandidateListBinding>,
        inflater: LayoutInflater
    ) {
        list.add(ActivityCandidateListBinding.inflate(layoutInflater))
    }

    override fun setup() {
        setUpView()
        setToolBar()
    }

    private fun setToolBar() {

        withBinding {
            setSupportActionBar(toolbar)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            val title = when (skillName) {

                ANDROID -> "ANDROID"

                IOS -> "iOS"

                BACKEND -> "BACKEND"

                else -> ""
            }
            supportActionBar?.title = title
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun setUpView() {
        skillName = intent.getStringExtra(SKILL_NAME) ?: ""
        withBinding {
            val candidateList = when (skillName) {
                ANDROID -> DataUtil.getAndroidCandidates()
                IOS -> DataUtil.getIosCandidates()
                BACKEND -> DataUtil.getBackendCandidates()
                else -> emptyList()
            }
            rvAndroid.layoutManager = LinearLayoutManager(this@CandidateListActivity)
            rvAndroid.adapter =
                CandidateListAdapter(this@CandidateListActivity, candidateList) { candidate ->
                    startActivity(
                        CandidateDetailActivity.newInstance(this@CandidateListActivity, candidate)
                    )
                }
        }
    }


    companion object {
        //to pass data from dashbord to list
        const val SKILL_NAME = "skill_name"
        const val ANDROID = "android"
        const val IOS = "ios"
        const val BACKEND = "backend"

        fun newInstance(context: Context, skillName: String) =
            Intent(context, CandidateListActivity::class.java).apply {
                putExtra(SKILL_NAME, skillName)
            }
    }
}