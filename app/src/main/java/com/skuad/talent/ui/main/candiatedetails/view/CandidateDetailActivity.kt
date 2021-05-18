package com.skuad.talent.ui.main.candiatedetails.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import com.skuad.talent.R
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.data.model.Candidate
import com.skuad.talent.databinding.ActivityCandidateProfileBinding
import com.skuad.talent.domain.entities.candidate.GetCandidateByAdmin
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.candiatedetails.viewmodel.CandidateDetailsViewModel
import timber.log.Timber
import javax.inject.Inject

class CandidateDetailActivity : BaseActivityVB<ActivityCandidateProfileBinding>() {

    @Inject
    lateinit var viewModel: CandidateDetailsViewModel

    lateinit var candidateData: String
    override fun attachBinding(
        list: MutableList<ActivityCandidateProfileBinding>,
        inflater: LayoutInflater
    ) {
        list.add(ActivityCandidateProfileBinding.inflate(layoutInflater))
    }

    override fun setup() {
        setToolBar()
        //   setUpView()
        setupObserver()
    }

    private fun setupObserver() {
        showLoading(true)
        viewModel.getCandidateDetails("67af0db0-bf93-4053-9d33-4a685f5ba97a", "")

        viewModel.candidateLiveData.observe(this, {
            showLoading(false)
            when (it) {
                is ResourceState.Success -> {
                    Timber.d("Candidate details is ${it.body}")
                    val candidateData = it.body
                    setUpView(candidateData)
                }

                is ResourceState.Failure -> {
                    Timber.e(it.exception)
                }
            }
        })
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

    private fun setUpView(candidateData: GetCandidateByAdmin) {
        // val candidateData = intent.getParcelableExtra<Candidate>(CANDIDATE_DETAILS)

        //  this.candidateData?.let {
        withBinding {
            tvCandidateName.text = candidateData.contact_info?.name
            tvDesignation.text = candidateData.experience.toString()
            tvSkills.text = candidateData.skills
        }
        //  }


    }

    companion object {
        //to pass data
        const val CANDIDATE_DETAILS = "CANDIDATE_DETAILS"
        fun newInstance(context: Context, candidateData: Candidate) =
            Intent(context, CandidateDetailActivity::class.java).apply {
                putExtra(CANDIDATE_DETAILS, candidateData)
            }
    }
}
