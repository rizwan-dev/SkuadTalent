package com.skuad.talent.ui.main.candidatelist.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.skuad.talent.R
import com.skuad.talent.databinding.ActivityCandidateListBinding
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.candiatedetails.view.CandidateDetailActivity
import com.skuad.talent.ui.main.candidatelist.adapter.CandidateListAdapter
import com.skuad.talent.utils.DataUtil
import timber.log.Timber

class NewCandidateListActivity : BaseActivityVB<ActivityCandidateListBinding>() {

    private lateinit var cardTitle: String

    override fun attachBinding(
        list: MutableList<ActivityCandidateListBinding>,
        inflater: LayoutInflater
    ) {
        list.add(ActivityCandidateListBinding.inflate(layoutInflater))
    }

    override fun setup() {
        setToolBar()
        setUpView()
    }

    private fun setToolBar() {

        withBinding {
            setSupportActionBar(toolbar)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            cardTitle = intent.getStringExtra(CARD_TITLE) ?: ""
            Timber.e("cardTitle------------" + cardTitle)
            supportActionBar?.title = cardTitle

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun setUpView() {

        withBinding {
            val candidateList = DataUtil.getAndroidCandidates()
            Timber.e("candidate list from DataUtil---->> " + candidateList)
            rvAndroid.layoutManager = LinearLayoutManager(this@NewCandidateListActivity)
            rvAndroid.adapter =
                CandidateListAdapter(this@NewCandidateListActivity, candidateList) { candidate ->
                    startActivity(
                        CandidateDetailActivity.newInstance(
                            this@NewCandidateListActivity,
                            candidate
                        )
                    )
                }
        }
    }


    companion object {
        //to pass data from dashbord to list
        const val CARD_TITLE = "card_title"


        fun newInstance(context: Context, name: String) =
            Intent(context, NewCandidateListActivity::class.java).apply {
                putExtra(CARD_TITLE, name)
            }
    }

}
