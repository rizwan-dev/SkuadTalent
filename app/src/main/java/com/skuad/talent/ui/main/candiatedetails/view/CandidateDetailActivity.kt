package com.skuad.talent.ui.main.candiatedetails.view

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import com.skuad.talent.R
import com.skuad.talent.base.entities.ResourceState
import com.skuad.talent.databinding.ActivityCandidateProfileBinding
import com.skuad.talent.domain.entities.candidate.GetCandidateByAdmin
import com.skuad.talent.extension.setSafeOnClickListener
import com.skuad.talent.extension.setVisibility
import com.skuad.talent.ui.base.BaseActivityVB
import com.skuad.talent.ui.main.candiatedetails.viewmodel.CandidateDetailsViewModel
import com.skuad.talent.utils.AppUtils
import es.voghdev.pdfviewpager.library.RemotePDFViewPager
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter
import es.voghdev.pdfviewpager.library.remote.DownloadFile
import es.voghdev.pdfviewpager.library.util.FileUtil
import timber.log.Timber
import java.io.File
import java.util.*
import javax.inject.Inject


class CandidateDetailActivity : BaseActivityVB<ActivityCandidateProfileBinding>(),
    DownloadFile.Listener {

    @Inject
    lateinit var viewModel: CandidateDetailsViewModel
    var adapter: PDFPagerAdapter? = null
    var remotePDFViewPager: RemotePDFViewPager? = null
    var resume: String? = null
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
        //    viewModel.getCandidateDetails("64b4c1c5-ccea-45be-a2bb-0123f7721b19", "")
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
            supportActionBar?.title = ""
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun setUpView(candidateData: GetCandidateByAdmin) {
        setUpResume(candidateData)
        setUpExperience(candidateData)
        withBinding {
            candidateData.contact_info?.let {
                val fullName = candidateData.contact_info?.name
                if (fullName.isNullOrEmpty()) {
                    imgProfile.text = "NA"
                } else {
                    val first = fullName?.substring(0, 1)
                    Timber.e("first letter is $first")
                    imgProfile.text = first.capitalize()

                }
                tvCandidateName.text = if (it.name.isNullOrEmpty()) "Name : NA" else it.name
                tvEmailAddress.text = if (it.email.isNullOrEmpty()) "Email : NA" else it.email
                tvAddress.text = if (it.address.isNullOrEmpty()) "Address : NA" else it.address
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
                tvSalary.text = "??? $amountWithComma"
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

    private fun setUpExperience(candidateData: GetCandidateByAdmin) {
        withBinding {
            if (!candidateData.experience.isNullOrEmpty()) {
                val experienceData = candidateData.experience[0]
                val role = experienceData.role
                val experience = experienceData.experience
                val employer = experienceData.company_id
                val roleString = if (role.isNullOrEmpty()) "Designation : NA" else role

                val experienceString =
                    if (experience.isNullOrEmpty()) "Experience : NA" else if(AppUtils.isExperienceInYears(experience)) "$experience years" else experience
                tvDesignation.text = "$roleString | $experienceString"
                val employerString =
                    if (employer.isNullOrEmpty()) "NA" else employer
                tvCurrentEmployer.text = employerString


            } else {
                tvDesignation.text = getString(R.string.designation_exp_not_available)
            }
        }
    }

    private fun setUpResume(candidateData: GetCandidateByAdmin) {
        withBinding {
            resume = candidateData.resumeUrl
            Timber.e("Resume url --->>${candidateData.resume}")
            if (candidateData.resume.isNullOrEmpty()) {
                tvNoResume.setVisibility(true)
                downloadResume.setVisibility(false)
            } else {
                tvNoResume.setVisibility(false)
                Timber.e("resume link ${candidateData.resumeUrl}")
                val f = File(candidateData.resume)
                val ext = f.extension
                Timber.e("file extension is == $ext")

                if (ext == "pdf") {
                    downloadResume.setVisibility(false)
                    val resumeUrl = candidateData.resumeUrl ?: ""
                    viewModel.resumeUrl = resumeUrl
                    remotePDFViewPager = RemotePDFViewPager(
                        this@CandidateDetailActivity,
                        resumeUrl,
                        this@CandidateDetailActivity
                    )
                    updateLayout()
                } else {
                    tvNoResume.setVisibility(true)
                    tvNoResume.text = getString(R.string.click_download)
                    downloadResume.setVisibility(true)
                    downloadResume.setSafeOnClickListener {
                        openResume(candidateData.resumeUrl!!)
                    }
                }
            }
        }
    }

    private fun updateLayout() {
        Timber.e("In update layout---")
        getBinding().webView.addView(
            remotePDFViewPager,
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
    }

    private fun openResume(resume: String) {
        showLoading(false)
        val browserIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(resume)
        )
        startActivity(browserIntent)

    }
    companion object {
        const val CANDIDATE_ID = "candidate_id"
        private const val STORAGE_PERMISSION_CODE: Int = 1000

        private const val SELECTED = "Registered"

        private const val REJECTED = "Disqualified"

        fun newInstance(context: Context, uid: String?) =
            Intent(context, CandidateDetailActivity::class.java).apply {
                putExtra(CANDIDATE_ID, uid)
            }
    }

    override fun onSuccess(url: String?, destinationPath: String?) {
        Timber.e("Success download : $url")
        adapter = PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url))
        remotePDFViewPager?.adapter = adapter
        showLoading(false)

    }

    override fun onFailure(e: Exception?) {
        Timber.e("Download Error is ${e!!.message}")
        showLoading(false)
        if (viewModel.resumeUrl.isNotEmpty()) {
            openResume(viewModel.resumeUrl)
        }
    }

    override fun onProgressUpdate(progress: Int, total: Int) {

    }

    override fun onDestroy() {
        adapter?.let { it.close() }
        super.onDestroy()
    }

}
