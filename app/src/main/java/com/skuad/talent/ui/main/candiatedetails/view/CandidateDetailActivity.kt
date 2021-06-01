package com.skuad.talent.ui.main.candiatedetails.view

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import com.skuad.talent.R
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.databinding.ActivityCandidateDetailsBinding
import com.skuad.talent.domain.entities.candidate.GetCandidateByAdmin
import com.skuad.talent.extension.setSafeOnClickListener
import com.skuad.talent.extension.setVisibility
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.candiatedetails.viewmodel.CandidateDetailsViewModel
import es.voghdev.pdfviewpager.library.RemotePDFViewPager
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter
import es.voghdev.pdfviewpager.library.remote.DownloadFile
import es.voghdev.pdfviewpager.library.util.FileUtil
import kotlinx.android.synthetic.main.alert_dialog.*
import timber.log.Timber
import javax.inject.Inject


class CandidateDetailActivity : BaseActivityVB<ActivityCandidateDetailsBinding>(),
    DownloadFile.Listener {

    @Inject
    lateinit var viewModel: CandidateDetailsViewModel

    var adapter: PDFPagerAdapter? = null

    var remotePDFViewPager: RemotePDFViewPager? = null
    override fun attachBinding(
        list: MutableList<ActivityCandidateDetailsBinding>,
        inflater: LayoutInflater
    ) {
        list.add(ActivityCandidateDetailsBinding.inflate(layoutInflater))
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


    private fun showSuccessAlert(stage: String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.alert_dialog)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.round_corner_10)
        dialog.setTitle(getString(R.string.profile_status))
        dialog.setCancelable(true)
        val textView: TextView = dialog.findViewById(R.id.tv_description)
        if (stage == SELECTED) {
            textView.text = getString(R.string.profile_shortlisted)
        } else {
            textView.text = getString(R.string.profile_rejected)
        }

        val btnOk: TextView = dialog.findViewById(R.id.btn_ok_dialog)
        btnOk.setOnClickListener {
            setResult(RESULT_OK)
            finish()
            dialog.dismiss()
        }
        val width = (resources.displayMetrics.widthPixels * 0.75).toInt()
        dialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show()
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
            Timber.d("In observer ")
            when (it) {
                is ResourceState.Success -> {
                    Timber.d("+++++State change success ${it.body}")
                    showSuccessAlert(viewModel.changeStage)

                }

                is ResourceState.Failure -> {
                    Timber.d("State change failed ")
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
        val resume =
            "candidateData.resume"

        withBinding {

            if (resume == null) {
                tvNoResume.setVisibility(true)
            } else {
                tvNoResume.setVisibility(false)
                showLoading(true)
                val resumeUrl = RESUME_BASE_URL + viewModel.userId
                remotePDFViewPager = RemotePDFViewPager(
                    this@CandidateDetailActivity,
                    resumeUrl,
                    this@CandidateDetailActivity
                )
                updateLayout()

            }

            candidateData.contact_info?.let {
                val fullName = candidateData.contact_info?.name
                val myName = fullName?.split(" ")
                val initial = myName?.fold("", { acc, s -> acc + s[0] })
                imgProfile.text = initial
                tvCandidateName.text = if (it.name.isNullOrEmpty()) "Name : NA" else it.name
                tvEmailAddress.text = if (it.email.isEmpty()) "Email : NA" else it.email
                tvAddress.text= if (it.address.isNullOrEmpty()) "Address :NA" else it.address
            }

            if (!candidateData.experience.isNullOrEmpty()) {
                val role = candidateData.role_id?.name
                val experience = candidateData.experience[0].experience
                val employer = candidateData.experience[0].company_id
                val roleString = if (role.isNullOrEmpty()) "Designation : NA" else role

                val experienceString =
                    if (experience.isNullOrEmpty()) "Experience : NA" else experience
                tvDesignation.text = "$roleString | $experienceString years"
                val employerString =
                    if (employer.isNullOrEmpty()) "Current Employer : NA" else employer
                tvCurrentEmployer.text = employerString


            } else {
                tvDesignation.text = getString(R.string.designation_exp_not_available)
            }

            if (!candidateData.experience.isNullOrEmpty()
                && !candidateData.experience[0].salary?.currency.isNullOrEmpty()
                && !candidateData.experience[0].salary?.amount?.toString().isNullOrEmpty()
            ) {

                val currency = candidateData.experience[0].salary?.currency
                val amount = candidateData.experience[0].salary?.amount?.toInt()
                val s: String = amount.toString()
                val d = java.lang.Double.valueOf(s)
                val amountWithComma = String.format("%,.0f", d)
                tvSalary.text = "₹" + " " + amountWithComma
            } else {
                tvSalary.text = getString(R.string.salary_not_available)
            }
            //
            if (!candidateData.preferences?.notice_period?.toString().isNullOrEmpty()) {
                tvNoticePeriod.text =
                    candidateData.preferences?.notice_period?.toInt().toString() + " days"
            } else {
                tvNoticePeriod.text = getString(R.string.notice_period_not_available)
            }


        }
    }

    private fun updateLayout() {
        getBinding().webView.addView(
            remotePDFViewPager,
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
    }

    companion object {
        const val CANDIDATE_ID = "candidate_id"

        private const val RESUME_BASE_URL = "https://hire-service.skuad.in/api/v1/document/candidate/"

        private const val SELECTED = "Registered"

        private const val REJECTED = "Disqualified"

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
