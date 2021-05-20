package com.skuad.talent.ui.main.candiatedetails.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.skuad.talent.R
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.databinding.ActivityCandidateProfileBinding
import com.skuad.talent.domain.entities.candidate.GetCandidateByAdmin
import com.skuad.talent.extension.setSafeOnClickListener
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.candiatedetails.viewmodel.CandidateDetailsViewModel
import timber.log.Timber
import javax.inject.Inject


class CandidateDetailActivity : BaseActivityVB<ActivityCandidateProfileBinding>() {

    @Inject
    lateinit var viewModel: CandidateDetailsViewModel


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

        addListeners()
    }

    private fun addListeners() {
        withBinding {
            btnSelected.setSafeOnClickListener {
                changeState(SELECTED)
            }

            btnRejected.setSafeOnClickListener {
                changeState(REJECTED)
            }
        }
    }

    private fun changeState(stage: String) {
        viewModel.userId?.let { uId ->
            showLoading(true)
            viewModel.changeCandidateState(uId, stage)
        }

    }

    private fun setupObserver() {
        val candidateId = intent.getStringExtra(CANDIDATE_ID)
        Timber.e("uid of Selected candidate ----> %s", candidateId)
        showLoading(true)
//        viewModel.getCandidateDetails("67af0db0-bf93-4053-9d33-4a685f5ba97a", "")
        candidateId?.let { viewModel.getCandidateDetails(it, "") }
        viewModel.candidateLiveData.observe(this, {
            showLoading(false)
            when (it) {
                is ResourceState.Success -> {
                    Timber.d("Candidate details is ${it.body}")
                    val candidateData = it.body
                    viewModel.userId = candidateData.uid
                    setUpView(candidateData)
                }

                is ResourceState.Failure -> {
                    Timber.e(it.exception)
                }
            }
        })

        viewModel.changeStateLiveData.observe(this, Observer {
            showLoading(false)
            when (it) {
                is ResourceState.Success -> {
                    Timber.d("Candidate details is ${it.body}")
                    setResult(RESULT_OK)
                    finish()
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
            if (!candidateData.experience.isNullOrEmpty()) {
                //string concatenation using a.plus(b) format
                tvDesignation.text = (candidateData.experience[0].experience ?: "") .plus(" years")
            }else{
                tvDesignation.text=getString(R.string.experience_not_available)
            }

            Timber.e("value of candidateData.skills" + candidateData.skills)
            if (!candidateData.skills.isNullOrEmpty()) {
                tvSkills.text = candidateData.skills.joinToString(", ")
            } else {
                tvSkills.text = getString(R.string.skills_not_available)
            }


        }
        //  }
    }

    companion object {
        //to pass data
        const val CANDIDATE_ID = "candidate_id"

        private const val SELECTED = "registered"

        private const val REJECTED = "disqualified"

        fun newInstance(context: Context, uid: String?) =
            Intent(context, CandidateDetailActivity::class.java).apply {
                putExtra(CANDIDATE_ID, uid)
            }
    }
}
