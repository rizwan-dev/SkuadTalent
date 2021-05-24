package com.skuad.talent.ui.main.candiatedetails.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import com.skuad.talent.R
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.databinding.ActivityCandidateProfileBinding
import com.skuad.talent.domain.entities.candidate.GetCandidateByAdmin
import com.skuad.talent.extension.setSafeOnClickListener
import com.skuad.talent.extension.setVisibility
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.candiatedetails.viewmodel.CandidateDetailsViewModel
import es.voghdev.pdfviewpager.library.RemotePDFViewPager
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter
import es.voghdev.pdfviewpager.library.remote.DownloadFile
import es.voghdev.pdfviewpager.library.util.FileUtil
import timber.log.Timber
import javax.inject.Inject


class CandidateDetailActivity : BaseActivityVB<ActivityCandidateProfileBinding>(), DownloadFile.Listener {

    @Inject
    lateinit var viewModel: CandidateDetailsViewModel

    var adapter: PDFPagerAdapter? = null

    var remotePDFViewPager: RemotePDFViewPager? = null

    override fun attachBinding(
        list: MutableList<ActivityCandidateProfileBinding>,
        inflater: LayoutInflater
    ) {
        list.add(ActivityCandidateProfileBinding.inflate(layoutInflater))
    }

    override fun setup() {
        setToolBar()
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
            supportActionBar?.title = "Profile Details"
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun setUpView(candidateData: GetCandidateByAdmin) {
        val resumeUrl = "https://firebasestorage.googleapis.com/v0/b/fir-test-f324a.appspot.com/o/Pavan%20Bilagi_Halodoc.pdf?alt=media&token=178c0ed0-e012-4e6d-a237-d86c8c72d670"

        withBinding {

            if(resumeUrl==null){
                tvNoResume.setVisibility(true)
            } else {
                tvNoResume.setVisibility(false)
                showLoading(true)
                 remotePDFViewPager = RemotePDFViewPager(this@CandidateDetailActivity, resumeUrl, this@CandidateDetailActivity)
                 updateLayout()

            }
            tvCandidateName.text = candidateData.contact_info?.name
            if (!candidateData.experience.isNullOrEmpty()) {
                tvDesignation.text = (candidateData.experience[0].experience ?: "") .plus(" years")
            }else{
                tvDesignation.text=getString(R.string.experience_not_available)
            }

            Timber.e("value of candidateData.skills %s", candidateData.skills)
            if (!candidateData.skills.isNullOrEmpty()) {
                tvSkills.text = candidateData.skills.joinToString(", ")
            } else {
                tvSkills.text = getString(R.string.skills_not_available)
            }


        }
    }

    private fun updateLayout() {
        getBinding().webView.addView(remotePDFViewPager,
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
    }

    companion object {
        const val CANDIDATE_ID = "candidate_id"

        private const val SELECTED = "registered"

        private const val REJECTED = "disqualified"

        fun newInstance(context: Context, uid: String?) =
            Intent(context, CandidateDetailActivity::class.java).apply {
                putExtra(CANDIDATE_ID, uid)
            }
    }

    override fun onSuccess(url: String?, destinationPath: String?) {
        adapter = PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url))
        remotePDFViewPager?.adapter = adapter
        showLoading(false)
    }

    override fun onFailure(e: Exception?) {
       showLoading(false)
    }

    override fun onProgressUpdate(progress: Int, total: Int) {

    }

    override fun onDestroy() {
        adapter?.let { it.close() }
        super.onDestroy()
    }
}
