package com.skuad.talent.ui.main.candidatelist

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import com.skuad.talent.R
import com.skuad.talent.data.model.Candidate
import com.skuad.talent.databinding.ActivityCandidateProfileBinding
import com.skuad.talent.ui.base.BaseActivityVB

class CandidateDetailActivity : BaseActivityVB<ActivityCandidateProfileBinding>() {
lateinit var candidateData: String
    override fun attachBinding(
        list: MutableList<ActivityCandidateProfileBinding>,
        inflater: LayoutInflater
    ) {
        list.add(ActivityCandidateProfileBinding.inflate(layoutInflater))
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
            supportActionBar?.title = "Profile Detail"
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun setUpView() {
        val candidateData =
            intent.getParcelableExtra<Candidate>(INTENT_PARCELABLE)
        Log.e("Check", "Candidate data is =======" + candidateData)
        withBinding {

        }

    }

    companion object {
        //to pass data
        const val INTENT_PARCELABLE = "OBJECT_INTENT"
        fun newInstance(context: Context, candidateData: String) =
            Intent(context, CandidateDetailActivity::class.java).apply {
                putExtra(CandidateDetailActivity.INTENT_PARCELABLE,candidateData)
            }
    }
}
